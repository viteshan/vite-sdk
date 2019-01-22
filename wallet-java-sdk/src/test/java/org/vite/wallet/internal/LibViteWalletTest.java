package org.vite.wallet.internal;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.binary.Base64;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.vite.wallet.internal.result.DerivationResult;
import org.vite.wallet.internal.result.SignDataResult;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;


public class LibViteWalletTest {
    LibViteWallet.Wallet wallet = null;

    @Before
    public void setUp() throws Exception {
        wallet = LibViteWallet.INSTANCE;
    }

    @Test
    public void TestHello() {
        String viteshan = wallet.hello("viteshan");
        Assert.assertEquals("Hello viteshan !", viteshan);
        System.out.println();
    }

    @Test
    public void TestWallet() {
        File file = new File("testdata/wallet");
        if (!file.exists()) {
            file.mkdirs();
        }
        // init wallet
        Result<Void> voidResult = wallet.initWallet(file.getAbsolutePath(), 256, false);
        Assert.assertTrue(voidResult.getError(), voidResult.success());


        // random mnemonic
        Result<String> randomMnemonic = wallet.randomMnemonic("", 24);
        Assert.assertTrue(randomMnemonic.getError(), voidResult.success());
        String mneMonic = randomMnemonic.getData();
        System.out.println(mneMonic);

        String tmpAddress = "vite_c6d9473637ae17af7caf085bc3a42d2f7e5f630d5db6cd731f";

        // mnemonic to address
        String tmpMnemonic = "curious smooth maid gate label impact pear crucial sugar horror visual toss talk flash income silly peace best act dress brand gas chief fetch";
        Result<String> addressResult = wallet.transformMnemonic(tmpMnemonic, "", "");
        Assert.assertTrue(addressResult.getError(), addressResult.success());
        Assert.assertEquals(addressResult.getData(), tmpAddress, addressResult.getData());

        // recover from mnemonic
        String passphrase = "123456";
        Result<String> recoverResult = wallet.recoverEntropyStoreFromMnemonic(tmpMnemonic, passphrase, "", "");
        Assert.assertTrue(recoverResult.getError(), recoverResult.success());
        Assert.assertEquals(recoverResult.getData(), tmpAddress, recoverResult.getData());


        // extract mnemonic
        Result<String> extractMnemonicResult = wallet.extractMnemonic(tmpAddress, passphrase);
        Assert.assertTrue(extractMnemonicResult.getError(), extractMnemonicResult.success());
        Assert.assertEquals(extractMnemonicResult.getData(), tmpMnemonic, extractMnemonicResult.getData());

        // list entropy store
        Result<List<String>> listResult = wallet.listAllEntropyFiles();
        Assert.assertTrue(listResult.getError(), listResult.success());
        Object[] listExpected = {file.getAbsolutePath() + "/" + tmpAddress};
        Assert.assertArrayEquals(JSON.toJSONString(listResult.getData()), listExpected, listResult.getData().toArray());

        Result<Void> unlockResult = wallet.unlock(tmpAddress, passphrase);
        Assert.assertTrue(unlockResult.getError(), unlockResult.success());

        // derive address by index
        Result<DerivationResult> derivationResultResult = wallet.deriveByIndex(tmpAddress, 0, "");
        Assert.assertTrue(derivationResultResult.getError(), derivationResultResult.success());
        Assert.assertEquals(JSON.toJSONString(derivationResultResult.getData()), tmpAddress, derivationResultResult.getData().getAddress());

        // getDataDir
        Result<String> dataDirResult = wallet.getDataDir();
        Assert.assertTrue(dataDirResult.getError(), dataDirResult.success());
        Assert.assertEquals(dataDirResult.getData(), file.getAbsolutePath(), dataDirResult.getData());


        final Base64 base64 = new Base64();
        final String text = "hello viteshan";
        String base64Text = base64.encodeToString(text.getBytes(Charset.forName("UTF-8")));

        // sign data
        Result<SignDataResult> signDataResultResult = wallet.signData(derivationResultResult.getData().getPrivateKey(), base64Text);
        Assert.assertTrue(signDataResultResult.getError(), signDataResultResult.success());
        Assert.assertEquals(JSON.toJSONString((signDataResultResult.getData())), base64Text, signDataResultResult.getData().getData());

        // verify sign
        Result<Boolean> verifyResult = wallet.verifySignature(signDataResultResult.getData().getPublicKey(), base64Text, signDataResultResult.getData().getSignature());
        Assert.assertTrue(verifyResult.getError(), verifyResult.success());
        Assert.assertTrue("verify sign fail" + JSON.toJSONString(signDataResultResult.getData()), verifyResult.getData());


    }

    @Test
    public void TestHash() {
        // 92b2a85109b4fac3d0e99374ef25f71e2e0918b3496cb949efc0dded70107c2f
        String block1 = "{\"blockType\":2,\"height\":\"2\",\"prevHash\":\"054bbebde1dd94df71b79410f7854339f16765f2e86e4328249f92a35471f4f2\",\"accountAddress\":\"vite_a8766cd3555692ff621f44db5c7f46cb8a80468d0fcceeb362\",\"publicKey\":\"21DCXxNYK/b3H4AOq5abzQlbgJLfTpFsHx3V3MVa4Zg=\",\"toAddress\":\"vite_e6dd9ae9d0334ad4f8b7707a6da5f626338169359e063a21cd\",\"fromBlockHash\":\"0000000000000000000000000000000000000000000000000000000000000000\",\"amount\":\"50000000000000000000000\",\"tokenId\":\"tti_5649544520544f4b454e6e40\",\"quota\":\"0\",\"fee\":\"0\",\"snapshotHash\":\"8b9e4efe1d61e545135f540ba45967ec905708cf1a5cb3547adb70e214967d4c\",\"data\":null,\"timestamp\":1543240161000,\"logHash\":null,\"difficulty\":\"157108864\",\"nonce\":\"Y1ir8sFu+VI=\",\"signature\":\"NGIJpOAPmuAUaD9cHRX8CtWRFMGVyhoD956mUOMvWW4YfoZnTyU39gKTvhW0+Y93nc4Y2COpyx1AFJdDf02IBQ==\"}";

        // 054bbebde1dd94df71b79410f7854339f16765f2e86e4328249f92a35471f4f2
        String block2 = "{\"blockType\":4,\"height\":\"1\",\"accountAddress\":\"vite_a8766cd3555692ff621f44db5c7f46cb8a80468d0fcceeb362\",\"toAddress\":\"vite_a8766cd3555692ff621f44db5c7f46cb8a80468d0fcceeb362\",\"fromBlockHash\":\"760b57339eecdcc3b0ddacc81bf920ccde3776ec1d43dd74d9aec68c5176cfbd\",\"amount\":\"50000000000000000000000\",\"tokenId\":\"tti_5649544520544f4b454e6e40\",\"quota\":\"0\",\"fee\":\"0\",\"snapshotHash\":\"b3591691a965e0ce85e88d463378f4b2b09d65ff49e2c0d805058e8977875d4f\",\"data\":null,\"timestamp\":1543230057000,\"logHash\":null,\"difficulty\":\"157108864\",\"nonce\":\"niU9YnfXg28=\"}";

        Result<String> hash1Result = wallet.computeHashForAccountBlock(block1);
        Assert.assertTrue(hash1Result.getError(), hash1Result.success());
        Assert.assertEquals(hash1Result.getData(), "92b2a85109b4fac3d0e99374ef25f71e2e0918b3496cb949efc0dded70107c2f", hash1Result.getData());

        Result<String> hash2Result = wallet.computeHashForAccountBlock(block2);
        Assert.assertTrue(hash2Result.getError(), hash2Result.success());
        Assert.assertEquals(hash2Result.getData(), "054bbebde1dd94df71b79410f7854339f16765f2e86e4328249f92a35471f4f2", hash2Result.getData());
    }
}
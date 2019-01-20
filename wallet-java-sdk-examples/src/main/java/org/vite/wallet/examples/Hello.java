package org.vite.wallet.examples;

import org.vite.wallet.internal.LibViteWallet;
import org.vite.wallet.internal.Result;

public class Hello {
    public static void main(String[] args) {
        LibViteWallet.Wallet wallet = LibViteWallet.INSTANCE;

        String text = wallet.hello("viteshan");
        System.out.println(text);



        // 92b2a85109b4fac3d0e99374ef25f71e2e0918b3496cb949efc0dded70107c2f
        String block1 = "{\"blockType\":2,\"height\":\"2\",\"prevHash\":\"054bbebde1dd94df71b79410f7854339f16765f2e86e4328249f92a35471f4f2\",\"accountAddress\":\"vite_a8766cd3555692ff621f44db5c7f46cb8a80468d0fcceeb362\",\"publicKey\":\"21DCXxNYK/b3H4AOq5abzQlbgJLfTpFsHx3V3MVa4Zg=\",\"toAddress\":\"vite_e6dd9ae9d0334ad4f8b7707a6da5f626338169359e063a21cd\",\"fromBlockHash\":\"0000000000000000000000000000000000000000000000000000000000000000\",\"amount\":\"50000000000000000000000\",\"tokenId\":\"tti_5649544520544f4b454e6e40\",\"quota\":\"0\",\"fee\":\"0\",\"snapshotHash\":\"8b9e4efe1d61e545135f540ba45967ec905708cf1a5cb3547adb70e214967d4c\",\"data\":null,\"timestamp\":1543240161000,\"logHash\":null,\"difficulty\":\"157108864\",\"nonce\":\"Y1ir8sFu+VI=\",\"signature\":\"NGIJpOAPmuAUaD9cHRX8CtWRFMGVyhoD956mUOMvWW4YfoZnTyU39gKTvhW0+Y93nc4Y2COpyx1AFJdDf02IBQ==\"}";

        // 054bbebde1dd94df71b79410f7854339f16765f2e86e4328249f92a35471f4f2
        String block2 = "{\"blockType\":4,\"height\":\"1\",\"accountAddress\":\"vite_a8766cd3555692ff621f44db5c7f46cb8a80468d0fcceeb362\",\"toAddress\":\"vite_a8766cd3555692ff621f44db5c7f46cb8a80468d0fcceeb362\",\"fromBlockHash\":\"760b57339eecdcc3b0ddacc81bf920ccde3776ec1d43dd74d9aec68c5176cfbd\",\"amount\":\"50000000000000000000000\",\"tokenId\":\"tti_5649544520544f4b454e6e40\",\"quota\":\"0\",\"fee\":\"0\",\"snapshotHash\":\"b3591691a965e0ce85e88d463378f4b2b09d65ff49e2c0d805058e8977875d4f\",\"data\":null,\"timestamp\":1543230057000,\"logHash\":null,\"difficulty\":\"157108864\",\"nonce\":\"niU9YnfXg28=\"}";

        Result<String> hash1Result = wallet.computeHashForAccountBlock(block1);
        System.out.println(hash1Result.getData());

        Result<String> hash2Result = wallet.computeHashForAccountBlock(block2);
        System.out.println(hash2Result.getData());
    }
}

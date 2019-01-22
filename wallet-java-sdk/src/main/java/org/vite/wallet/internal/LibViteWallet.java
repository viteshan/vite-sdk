package org.vite.wallet.internal;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import org.vite.wallet.internal.result.DerivationResult;
import org.vite.wallet.internal.result.EntropyResult;
import org.vite.wallet.internal.result.SignDataResult;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class LibViteWallet {

    public interface Wallet {
        Result<Void> initWallet(String dataDir, int maxSearchIndex, boolean useLightScrypt);

        Result<List<String>> listAllEntropyFiles();

        Result<Void> unlock(String entropyStore, String passphrase);

        Result<Boolean> isUnlocked(String entropyStore);

        Result<Void> lock(String entropyStore);

        Result<Void> addEntropyStore(String entropyStore);

        Result<Void> removeEntropyStore(String entropyStore);

        Result<String> recoverEntropyStoreFromMnemonic(String mnemonic, String newPassphrase);

        Result<EntropyResult> newMnemonicAndEntropyStore(String passphrase, int mnemonicSize);

        Result<DerivationResult> deriveByFullPath(String entropyStore, String fullpath);

        Result<DerivationResult> deriveByIndex(String entropyStore, int index);

        Result<String> extractMnemonic(String entropyStore, String passphrase);

        Result<String> getDataDir();

        Result<String> entropyStoreToAddress(String entropyStore);

        Result<String> hash256(String dataBase64);

        Result<String> hash(int size, String dataBase64);

        Result<SignDataResult> signData(String privHex, String dataBase64);

        Result<Boolean> verifySignature(String pubBase64, String dataBase64, String signatureBase64);

        Result<String> pubkeyToAddress(String pubBase64);

        Result<String> transformMnemonic(String mnemonic);

        Result<String> randomMnemonic(int mnemonicSize);

        Result<String> computeHashForAccountBlock(String blockJson);

        String hello(String name);
    }

    public static Wallet INSTANCE = new InternalWallet();

    private static class InternalWallet implements Wallet {
        private String LIB_NAME = "vitewallet";
        private final Lib nativeLib;

        public InternalWallet() {
            InputStream is = InternalWallet.class.getClassLoader().getResourceAsStream(System.mapLibraryName(LIB_NAME));
            if (is == null) {
                throw new RuntimeException("can't load vitewallet lib[" + System.mapLibraryName(LIB_NAME) + "].");
            }
            try {
                Loader.loadFromStream(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.nativeLib = Native.load(LIB_NAME, Lib.class);
        }

        public <R> R convertAndFreePointer(Pointer p, TypeReference<R> clazz) {
            String result = p.getString(0);
            this.nativeLib.FreeCchar(p);
            return JSONObject.parseObject(result, clazz);
        }


        @Override
        public Result<Void> initWallet(String dataDir, int maxSearchIndex, boolean useLightScrypt) {
            Pointer point = nativeLib.InitWallet(dataDir, maxSearchIndex, useLightScrypt);
            Result<Void> voidResult = convertAndFreePointer(point, new TypeReference<Result<Void>>() {
            });
            return voidResult;
        }

        @Override
        public Result<List<String>> listAllEntropyFiles() {
            return convertAndFreePointer(nativeLib.ListAllEntropyFiles(), new TypeReference<Result<List<String>>>() {
            });
        }

        @Override
        public Result<Void> unlock(String entropyStore, String passphrase) {
            return convertAndFreePointer(nativeLib.Unlock(entropyStore, passphrase), new TypeReference<Result<Void>>() {
            });
        }

        @Override
        public Result<Boolean> isUnlocked(String entropyStore) {
            return convertAndFreePointer(nativeLib.IsUnlocked(entropyStore), new TypeReference<Result<Boolean>>() {
            });
        }

        @Override
        public Result<Void> lock(String entropyStore) {
            return convertAndFreePointer(nativeLib.Lock(entropyStore), new TypeReference<Result<Void>>() {
            });
        }

        @Override
        public Result<Void> addEntropyStore(String entropyStore) {
            return convertAndFreePointer(nativeLib.AddEntropyStore(entropyStore), new TypeReference<Result<Void>>() {
            });
        }

        @Override
        public Result<Void> removeEntropyStore(String entropyStore) {
            return convertAndFreePointer(nativeLib.RemoveEntropyStore(entropyStore), new TypeReference<Result<Void>>() {
            });
        }

        @Override
        public Result<String> recoverEntropyStoreFromMnemonic(String mnemonic, String newPassphrase) {
            return convertAndFreePointer(nativeLib.RecoverEntropyStoreFromMnemonic(mnemonic, newPassphrase, "", ""), new TypeReference<Result<String>>() {
            });
        }

        @Override
        public Result<EntropyResult> newMnemonicAndEntropyStore(String passphrase, int mnemonicSize) {
            return convertAndFreePointer(nativeLib.NewMnemonicAndEntropyStore(passphrase, "", "", mnemonicSize), new TypeReference<Result<EntropyResult>>() {
            });
        }

        @Override
        public Result<DerivationResult> deriveByFullPath(String entropyStore, String fullpath) {
            return convertAndFreePointer(nativeLib.DeriveByFullPath(entropyStore, fullpath, ""), new TypeReference<Result<DerivationResult>>() {
            });
        }

        @Override
        public Result<DerivationResult> deriveByIndex(String entropyStore, int index) {
            return convertAndFreePointer(nativeLib.DeriveByIndex(entropyStore, index, ""), new TypeReference<Result<DerivationResult>>() {
            });
        }

        @Override
        public Result<String> extractMnemonic(String entropyStore, String passphrase) {
            return convertAndFreePointer(nativeLib.ExtractMnemonic(entropyStore, passphrase), new TypeReference<Result<String>>() {
            });
        }

        @Override
        public Result<String> getDataDir() {
            return convertAndFreePointer(nativeLib.GetDataDir(), new TypeReference<Result<String>>() {
            });
        }

        @Override
        public Result<String> entropyStoreToAddress(String entropyStore) {
            return convertAndFreePointer(nativeLib.EntropyStoreToAddress(entropyStore), new TypeReference<Result<String>>() {
            });
        }

        @Override
        public Result<String> hash256(String dataBase64) {
            return convertAndFreePointer(nativeLib.Hash256(dataBase64), new TypeReference<Result<String>>() {
            });
        }

        @Override
        public Result<String> hash(int size, String dataBase64) {
            return convertAndFreePointer(nativeLib.Hash(size, dataBase64), new TypeReference<Result<String>>() {
            });
        }

        @Override
        public Result<SignDataResult> signData(String privHex, String dataBase64) {
            return convertAndFreePointer(nativeLib.SignData(privHex, dataBase64), new TypeReference<Result<SignDataResult>>() {
            });
        }

        @Override
        public Result<Boolean> verifySignature(String pubBase64, String dataBase64, String signatureBase64) {
            return convertAndFreePointer(nativeLib.VerifySignature(pubBase64, dataBase64, signatureBase64), new TypeReference<Result<Boolean>>() {
            });
        }

        @Override
        public Result<String> pubkeyToAddress(String pubBase64) {
            return convertAndFreePointer(nativeLib.PubkeyToAddress(pubBase64), new TypeReference<Result<String>>() {
            });
        }

        @Override
        public Result<String> transformMnemonic(String mnemonic) {
            return convertAndFreePointer(nativeLib.TransformMnemonic(mnemonic, "", ""), new TypeReference<Result<String>>() {
            });
        }

        @Override
        public Result<String> randomMnemonic(int mnemonicSize) {
            return convertAndFreePointer(nativeLib.RandomMnemonic("", mnemonicSize), new TypeReference<Result<String>>() {
            });
        }

        @Override
        public Result<String> computeHashForAccountBlock(String blockJson) {
            return convertAndFreePointer(nativeLib.ComputeHashForAccountBlock(blockJson), new TypeReference<Result<String>>() {
            });
        }

        @Override
        public String hello(String name) {
            Pointer hello = nativeLib.Hello(name);
            String result = hello.getString(0);
            nativeLib.FreeCchar(hello);
            return result;
        }
    }


    private interface Lib extends Library {
        Pointer InitWallet(String dataDir, int maxSearchIndex, boolean useLightScrypt);

        Pointer ListAllEntropyFiles();

        Pointer Unlock(String entropyStore, String passphrase);

        Pointer IsUnlocked(String entropyStore);

        Pointer Lock(String entropyStore);

        Pointer AddEntropyStore(String entropyStore);

        Pointer RemoveEntropyStore(String entropyStore);

        Pointer RecoverEntropyStoreFromMnemonic(String mnemonic, String newPassphrase, String language, String extensionWord);

        Pointer NewMnemonicAndEntropyStore(String passphrase, String language, String extensionWord, int mnemonicSize);

        Pointer DeriveByFullPath(String entropyStore, String fullpath, String extensionWord);

        Pointer DeriveByIndex(String entropyStore, int index, String extensionWord);

        Pointer ExtractMnemonic(String entropyStore, String passphrase);

        Pointer GetDataDir();

        Pointer EntropyStoreToAddress(String entropyStore);

        Pointer Hash256(String dataBase64);

        Pointer Hash(int size, String dataBase64);

        Pointer SignData(String privHex, String dataBase64);

        Pointer VerifySignature(String pubBase64, String dataBase64, String signatureBase64);

        Pointer PubkeyToAddress(String pubBase64);

        Pointer TransformMnemonic(String mnemonic, String language, String extensionWord);

        Pointer RandomMnemonic(String language, int mnemonicSize);

        Pointer ComputeHashForAccountBlock(String blockJson);

        Pointer Hello(String name);

        void FreeCchar(Pointer p);
    }
}



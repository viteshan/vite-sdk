package org.vite.wallet.internal.result;

public class EntropyResult {
    private String mnemonic;
    private String entropyStore;

    public String getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }

    public String getEntropyStore() {
        return entropyStore;
    }

    public void setEntropyStore(String entropyStore) {
        this.entropyStore = entropyStore;
    }

    @Override
    public String toString() {
        return "EntropyResult{" +
                "mnemonic='" + mnemonic + '\'' +
                ", entropyStore='" + entropyStore + '\'' +
                '}';
    }
}

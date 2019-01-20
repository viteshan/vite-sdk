import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Loader {
    public final static void loadFromStream(InputStream is) throws IOException {
        FileOutputStream os = null;
        File dstFile;
        try {
            dstFile = File.createTempFile("jni", "." + "native");
        } catch (IOException ex) {
            throw new IOException("Failed to create temporary file:." + "dylib");
        }
        try {
            dstFile.deleteOnExit();
            os = new FileOutputStream(dstFile);
            ReadableByteChannel srcChannel = Channels.newChannel(is);

            for (long pos = 0; is.available() > 0; ) {
                pos += os.getChannel().transferFrom(srcChannel, pos, Math.max(4096, is.available()));
            }

            os.close();
            os = null;

            System.load(dstFile.getAbsolutePath());
            dstFile.delete();
        } catch (IOException ex) {
            throw new UnsatisfiedLinkError(ex.getMessage());
        } finally {
            if (os != null) {
                os.close();
            }
            is.close();
        }
    }
}

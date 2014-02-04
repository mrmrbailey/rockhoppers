/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.file;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mxbailey
 */
public class FileLoader {

    /**
     * Naming convention for Game Bundles.
     */
    private static final String GAME_BUNDLE_SUFFIX = "-bundle.jar";
    private static final String DAILY_PLAY_GAME_BUNDLE_NAME = "daily-bundle.jar";
    private static final String DREAMNUMBER_GAME_BUNDLE_NAME = "dream-bundle.jar";

    /**
     * Retrieve all jars in the bundle filepath, and verify the validity of
     * them.
     *
     * @param bundleFilepath
     * @param publicKey
     * @return
     * @throws IOException
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws NoSuchProviderException
     * @throws SignatureException
     */
    protected static URL[] getGameBundleURLs(String bundleFilepath) {
        URL[] urls;
        // Look for any *-bundle.jars in the specified directory
        File dir = new File(bundleFilepath);

        FilenameFilter filter = new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
/*                if (name.equals(DAILY_PLAY_GAME_BUNDLE_NAME) || name.equals(DREAMNUMBER_GAME_BUNDLE_NAME)) {
                    return false;
                }
                return name.endsWith(GAME_BUNDLE_SUFFIX);
                * 
                */
                return !(name.equals(DAILY_PLAY_GAME_BUNDLE_NAME) || name.equals(DREAMNUMBER_GAME_BUNDLE_NAME))
                        && name.endsWith(GAME_BUNDLE_SUFFIX);
            }
        };

        ArrayList urlList = new ArrayList();
        File[] bundles = dir.listFiles(filter);

        for (int i = 0; i < bundles.length; i++) {
            try {
                urlList.add(bundles[i].toURI().toURL());
            } catch (MalformedURLException ex) {
                Logger.getLogger(FileLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        urls = (URL[]) urlList.toArray(new URL[urlList.size()]);

        Logger.getLogger("getGameBundleURLs - bundleFilepath: " + bundleFilepath + " urls found: " + urls.length);
        return urls;
    }
}

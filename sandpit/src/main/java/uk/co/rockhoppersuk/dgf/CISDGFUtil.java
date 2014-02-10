/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.dgf;

import com.camelotinteractive.dgf.DGFUtil;
import java.io.IOException;
import java.net.URL;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mxbailey
 */
public class CISDGFUtil extends DGFUtil {

    public static URL[] getULRS() {
        PublicKey publicKey = null;
        URL[] urls;

        String gameBundleLocation = "C:\\perforce\\ESD\\DEV\\speedy\\wsad\\was5\\data\\appServer\\games";
        String keyStoreName = gameBundleLocation + "/" + "signing_cert.jks";
        List failedBundles = new ArrayList();
        try {
            publicKey = getPublicKeyFromKeyStore(keyStoreName);
        } catch (KeyStoreException ex) {
            Logger.getLogger(CISDGFUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CISDGFUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(CISDGFUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CISDGFUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return getGameBundleURLs(gameBundleLocation, publicKey, failedBundles);

    }
}

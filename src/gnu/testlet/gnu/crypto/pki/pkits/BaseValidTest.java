/* BaseValidTest.java -- superclass of "valid" tests.
   Copyright (C) 2003  Free Software Foundation, Inc.

   Distributed under the GPL; see the file `COPYING' */


package gnu.testlet.gnu.crypto.pki.pkits;

import java.security.cert.*;
import java.util.*;

import gnu.testlet.TestHarness;
import gnu.testlet.Testlet;

public abstract class BaseValidTest extends PKITS implements Testlet
{

  // Fields.
  // -------------------------------------------------------------------------

  public static final String PROVIDER = System.getProperty("pkits.provider", "GNU-PKI");
  public static final String TRUST_ANCHOR_CERT = "data/certs/TrustAnchorRootCertificate.crt";
  public static final String TRUST_ANCHOR_CRL = "data/crls/TrustAnchorRootCRL.crl";

  protected String[] certPath;
  protected String[] crls;
  protected String[] certs;

  // Constructors.
  // -------------------------------------------------------------------------

  protected BaseValidTest(String[] certPath, String[] crls, String[] certs)
  {
    if (certPath == null || crls == null || certs == null)
      throw new NullPointerException();
    this.certPath = certPath;
    this.crls = crls;
    this.certs = certs;
  }

  protected BaseValidTest(String[] certPath, String[] crls)
  {
    this(certPath, crls, new String[0]);
  }

  // Instance method.
  // -------------------------------------------------------------------------

  public void test(TestHarness harness)
  {
    String testName = getClass().getName();
    if (testName.lastIndexOf ('.') > 0)
      testName = testName.substring (testName.lastIndexOf ('.') + 1);
    harness.checkPoint(testName);
    try
      {
        CertificateFactory factory = CertificateFactory.getInstance("X.509", PROVIDER);
        TrustAnchor anchor = new TrustAnchor((X509Certificate) factory.generateCertificate(getClass().getResourceAsStream(TRUST_ANCHOR_CERT)), null);
        List pathList = new ArrayList(certPath.length);
        for (int i = 0; i < certPath.length; i++)
          {
            pathList.add(factory.generateCertificate(getClass().getResourceAsStream(certPath[i])));
          }
        List crlsAndCerts = new ArrayList(crls.length + certs.length + 1);
        crlsAndCerts.add(factory.generateCRL(getClass().getResourceAsStream(TRUST_ANCHOR_CRL)));
        for (int i = 0; i < crls.length; i++)
          {
            crlsAndCerts.add(factory.generateCRL(getClass().getResourceAsStream(crls[i])));
          }
        for (int i = 0; i < certs.length; i++)
          {
            crlsAndCerts.add(factory.generateCertificate(getClass().getResourceAsStream(certs[i])));
          }
        CertPath path = factory.generateCertPath(pathList);
        CertStore certStore = CertStore.getInstance("Collection", new CollectionCertStoreParameters(crlsAndCerts), PROVIDER);
        PKIXParameters params = new PKIXParameters(Collections.singleton(anchor));
        params.addCertStore(certStore);
        params.setExplicitPolicyRequired(false);
        params.setInitialPolicies(Collections.singleton(PKITS.ANY_POLICY));
        params.setPolicyMappingInhibited(false);
        params.setAnyPolicyInhibited(false);
        setupAdditionalParams(params);
        CertPathValidator validator = CertPathValidator.getInstance("PKIX", PROVIDER);
        CertPathValidatorResult result = validator.validate(path, params);
        verify (harness, result);
      }
    catch (Exception x)
      {
        harness.debug(x);
        harness.fail(x.toString());
      }
  }

  /**
   * Subclasses should override this method to add any additional parameters
   * before the path verification is run.
   *
   * @param params The parameters.
   */
  protected void setupAdditionalParams (PKIXParameters params)
  {
  }

  /**
   * Subclasses should override this method to perform any final verification
   * on the certification path validation result. The default implementation
   * simply prints the policy tree (if we are configured to be verbose) and
   * passes the test.
   *
   * @param harness The test harness.
   * @param result The validation result. This will almost always be an
   *        instance of {@link PKIXCertPathValidatorResult}.
   * @throws Exception If verification fails unexpectedly.
   */
  protected void verify (TestHarness harness,
                         CertPathValidatorResult result)
    throws Exception
  {
    harness.verbose(((PKIXCertPathValidatorResult) result).getPolicyTree().toString());
    harness.check(true);
  }
}

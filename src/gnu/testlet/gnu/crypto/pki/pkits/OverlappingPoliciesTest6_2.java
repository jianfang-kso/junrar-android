/* OverlappingPoliciesTest6_2.java
   Copyright (C) 2004  Free Software Foundation, Inc.

   Distributed under the GPL; see the file `COPYING' */

// Tags: PKITS

package gnu.testlet.gnu.crypto.pki.pkits;

import java.security.cert.PKIXParameters;
import java.util.Collections;

public class OverlappingPoliciesTest6_2 extends BaseValidTest
{
  public OverlappingPoliciesTest6_2()
  {
    super (new String[] { "data/certs/OverlappingPoliciesTest6EE.crt",
                          "data/certs/PoliciesP1234subsubCAP123P12Cert.crt",
                          "data/certs/PoliciesP1234subCAP123Cert.crt",
                          "data/certs/PoliciesP1234CACert.crt" },
           new String[] { "data/crls/PoliciesP1234subsubCAP123P12CRL.crl",
                          "data/crls/PoliciesP1234subCAP123CRL.crl",
                          "data/crls/PoliciesP1234CACRL.crl" });
  }

  protected void setupAdditionalParams (PKIXParameters params)
  {
    params.setExplicitPolicyRequired (true);
    params.setInitialPolicies (Collections.singleton (NIST_TEST_POLICY_1));
  }
}

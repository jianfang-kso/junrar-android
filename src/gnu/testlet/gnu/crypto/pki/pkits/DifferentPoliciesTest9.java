/* DifferentPoliciesTest7.java
   Copyright (C) 2004  Free Software Foundation, Inc.

   Distributed under the GPL; see the file `COPYING' */

// Tags: PKITS

package gnu.testlet.gnu.crypto.pki.pkits;

import java.security.cert.PKIXParameters;
import java.util.Collections;

public class DifferentPoliciesTest9 extends BaseInvalidTest
{
  public DifferentPoliciesTest9()
  {
    super (new String[] { "data/certs/DifferentPoliciesTest9EE.crt",
                          "data/certs/PoliciesP123subsubsubCAP12P2P1Cert.crt",
                          "data/certs/PoliciesP123subsubCAP12P1Cert.crt",
                          "data/certs/PoliciesP123subCAP12Cert.crt",
                          "data/certs/PoliciesP123CACert.crt" },
           new String[] { "data/crls/PoliciesP123subsubsubCAP12P2P1CRL.crl",
                          "data/crls/PoliciesP123subsubCAP12P1CRL.crl",
                          "data/crls/PoliciesP123subCAP12CRL.crl",
                          "data/crls/PoliciesP123CACRL.crl" });
  }

  protected void setupAdditionalParams (PKIXParameters params)
  {
    params.setInitialPolicies (Collections.EMPTY_SET);
    params.setAnyPolicyInhibited (true);
  }
}

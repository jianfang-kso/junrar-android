/* InvalidkeyUsageCriticalkeyCertSignFalseTest1.java
   Copyright (C) 2003  Free Software Foundation, Inc.

   Distributed under the GPL. See the file `COPYING' */

// Tags: PKITS

package gnu.testlet.gnu.crypto.pki.pkits;

public class InvalidkeyUsageCriticalkeyCertSignFalseTest1 extends BaseInvalidTest
{
  public InvalidkeyUsageCriticalkeyCertSignFalseTest1()
  {
    super(new String[] { "data/certs/InvalidkeyUsageCriticalkeyCertSignFalseTest1EE.crt",
                         "data/certs/keyUsageCriticalkeyCertSignFalseCACert.crt" },
          new String[] { "data/crls/keyUsageCriticalkeyCertSignFalseCACRL.crl" });
  }
}

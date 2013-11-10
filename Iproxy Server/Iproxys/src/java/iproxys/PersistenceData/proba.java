/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iproxys.PersistenceData;

import iproxys.performblock.PerformIpBlock;
import java.util.Date;

/**
 *
 * @author root
 */
public class proba {

    public static void main(String[] as) {
      TemporaryBlockedEntity tem = new TemporaryBlockedEntity();
      tem.setBlockedOnTimeDate(new Date());
    }
}

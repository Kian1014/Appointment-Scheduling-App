package interfaces;

import model.Customer;

/**
 * Interface class to implement a lambda expression.
 */

public interface CheckNum {

    /**
     * Abstract method to check two numbers.
     * @param id Id to be compared.
     * @param otherId Other Id to be compared.
     * @return Returns boolean from comparison of two ids.
     */

    boolean checkNum(int id, int otherId);

}

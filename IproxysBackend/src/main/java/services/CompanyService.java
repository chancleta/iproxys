package services;

/**
 * Created by lupena on 2/5/2016.
 */

import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;
import models.Company;
import models.Employee;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

/**
 *
 */
public class CompanyService  {

    private static CompanyService  companyService;
    private List<Company> companyList;

    /**
     *
     */
    private CompanyService(){
        setCompanyStartUpList();
    }

    /**
     *
     */
    private void setCompanyStartUpList(){
        companyList = new LinkedList<>();

        int amountOfCompaniesToCreate = (int) (Math.random() * (15 - 8)) + 8;// A random amount between 8 and 15

        IntStream.range(0, amountOfCompaniesToCreate).forEach(
            creationIndex -> {

                Fairy fairyCompany = Fairy.create();
                io.codearte.jfairy.producer.company.Company randomCompanyData = fairyCompany.company();
                Person randomPersonData = fairyCompany.person();
                Company company = new Company();

                company.setEmail(randomCompanyData.email());
                company.setName(randomCompanyData.name());
                company.setCountry("USA");
                company.setCity(randomPersonData.getAddress().getCity());
                company.setAddress(randomPersonData.getAddress().toString());
                company.setPhoneNumber(randomPersonData.telephoneNumber());
                company.setCompanyID(getRandomID());

                int amountOfEmployees  = (int) (Math.random() * (6 - 1)) + 1;

                IntStream.range(0, amountOfEmployees).forEach(
                        employeeIndex -> {
                            Employee employee = new Employee();
                            Fairy fairyEmployee = Fairy.create();
                            employee.setName(fairyEmployee.person().fullName());
                            employee.setEmployeeID(getRandomID());
                            company.getEmployees().add(employee);
                        }
                );

                companyList.add(company);
            }
        );

    }

    /**
     *
     * @return
     */
    public long getRandomID(){
        return  Long.parseLong(UUID.randomUUID().toString().replaceAll("[a-z]|-","").substring(0,8));
    }

    /**
     *
     * @return
     */
    public static CompanyService getInstance() {

        if(companyService == null)
            companyService = new CompanyService();

        return companyService;
    }

    /**
     *
     * @return
     */
    public List<Company> getAllCompanies(){
        return companyList;
    }

    /**
     *
     * @param company
     */
    public void AddCompany(Company company){
        getAllCompanies().add(company);
    }

    /**
     *
     * @param companyID
     * @return
     */
    public Company getCompanyByID(Long companyID){
        Optional<Company> firstCompany = getAllCompanies().stream().filter(co -> co.getCompanyID().longValue() == companyID.longValue()).findFirst();
        return firstCompany.isPresent() ? firstCompany.get() : null;
    }

    /**
     *
     * @param stringNumber
     * @return
     */
    public boolean isNumeric(String stringNumber){

        try{
            Double.parseDouble(stringNumber);
            return true;
        }catch (NumberFormatException ex)
        {
            return false;
        }

    }

    /**
     *
     * @param newCompanyData
     */
    public void updateCompany(Company newCompanyData){
        removeCompanyByID(newCompanyData.getCompanyID());
        getAllCompanies().add(newCompanyData);
    }

    /**
     *
     * @param company
     */
    private void removeCompany(Company company){
        getAllCompanies().remove(company);
    }

    /**
     *
     * @param companyID
     */
    public void removeCompanyByID(Long companyID ){
      removeCompany(getCompanyByID(companyID));
    }

    public Employee getEmployeeFromCompany(Long employeeID, Company company){
        Optional<Employee> firstEmployee = company.getEmployees().stream().filter(co -> co.getEmployeeID().longValue() == employeeID.longValue()).findFirst();
        return firstEmployee.isPresent() ? firstEmployee.get() : null;
    }
}


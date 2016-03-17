package api;

import services.FeedService;

import static app.ResponseManager.toJson;
import static spark.Spark.get;
import static spark.Spark.options;

/**
 * Created by lupena on 2/5/2016.
 */
public class FeedListController extends BaseJsonController{

    public FeedListController(final FeedService feedService) {
        super();


        get("/feed", (request, response) -> { return feedService.getFecthFeedList(10);}, toJson());

        options("/feed", (request, response) -> "");

//        post("/companies", (request, response) -> {
//
//            Company company = CustomGson.Gson().fromJson(request.body(), Company.class);
//            company.setCompanyID(companyService.getRandomID());
//            if(!company.isValid())
//                throw new InvalidCompanyDataException(company.getErrorMessage());
//
//            companyService.AddCompany(company);
//
//            /**
//             * Returning the Company object, we should return 201 and Location Header
//             *  response.status(201);
//             *  response.header("Location","/companies/"+company.getCompanyID());
//             */
//             return company;
//;
//
//        }, toJson());
//
//        get("/companies/:id", (request, response) -> {
//
//            String CompanyID = request.params(":id");
//
//            if(!companyService.isNumeric(CompanyID))
//                throw new InvalidCompanyDataException("Invalid Company ID");
//
//            Company storedCompany = companyService.getCompanyByID(Long.parseLong(CompanyID));
//
//            if(storedCompany == null)
//                throw new InvalidCompanyDataException("Company wasn't found");
//
//            return  storedCompany;
//
//        },toJson());
//
//        put("/companies/:id", (request, response) -> {
//
//            String CompanyID = request.params(":id");
//
//            if(!companyService.isNumeric(CompanyID))
//                throw new InvalidCompanyDataException("Invalid Company ID");
//
//            Company storedCompany = companyService.getCompanyByID(Long.parseLong(CompanyID));
//
//            if(storedCompany == null)
//                throw new InvalidCompanyDataException("Company wasn't found");
//
//            Company newCompanyData = CustomGson.Gson().fromJson(request.body(), Company.class);
//            newCompanyData.setCompanyID(Long.parseLong(CompanyID));
//
//            if(!newCompanyData.isValid())
//                throw new InvalidCompanyDataException(newCompanyData.getErrorMessage());
//
//          companyService.updateCompany(newCompanyData);
//            return  newCompanyData;
//        },toJson());
//
//        delete("/companies/:id", (request, response) -> {
//
//            String CompanyID = request.params(":id");
//
//            if(!companyService.isNumeric(CompanyID))
//                throw new InvalidCompanyDataException("Invalid Company ID");
//
//            Company storedCompany = companyService.getCompanyByID(Long.parseLong(CompanyID));
//
//            if(storedCompany == null)
//                throw new InvalidCompanyDataException("Company wasn't found");
//
//            companyService.removeCompanyByID(storedCompany.getCompanyID());
//
//            return  new GenericMessage("Entry deleted");
//
//        },toJson());
//
//        /**
//         * Returns all the employees from the specific company
//         */
//        get("/companies/:id/employees", (request, response) -> {
//
//            String CompanyID = request.params(":id");
//
//            if(!companyService.isNumeric(CompanyID))
//                throw new InvalidCompanyDataException("Invalid Company ID");
//
//            Company storedCompany = companyService.getCompanyByID(Long.parseLong(CompanyID));
//
//            if(storedCompany == null)
//                throw new InvalidCompanyDataException("Company wasn't found");
//
//            return storedCompany.getEmployees();
//        },toJson());
//
//
//        /**
//         * Returns the specific employee from the specific company
//         */
//        get("/companies/:id/employees/:employeeid", (request, response) -> {
//
//            String CompanyID = request.params(":id");
//            String EmployeeID = request.params(":employeeid");
//
//            if(!companyService.isNumeric(CompanyID))
//                throw new InvalidCompanyDataException("Invalid Company ID");
//
//            if(!companyService.isNumeric(EmployeeID))
//                throw new InvalidCompanyDataException("Invalid Employee ID");
//
//            Company storedCompany = companyService.getCompanyByID(Long.parseLong(CompanyID));
//
//            if(storedCompany == null)
//                throw new InvalidCompanyDataException("Company wasn't found");
//
//            Employee storedEmployee = companyService.getEmployeeFromCompany(Long.parseLong(EmployeeID),storedCompany);
//
//            if(storedEmployee == null)
//                throw new InvalidCompanyDataException("Employee wasn't found");
//
//            return storedEmployee;
//        },toJson());
//
//        /**
//         * Returns the specific employee from the specific company
//         */
//        get("/companies/:id/employees/:employeeid", (request, response) -> {
//
//            String CompanyID = request.params(":id");
//            String EmployeeID = request.params(":employeeid");
//
//            if(!companyService.isNumeric(CompanyID))
//                throw new InvalidCompanyDataException("Invalid Company ID");
//
//            if(!companyService.isNumeric(EmployeeID))
//                throw new InvalidCompanyDataException("Invalid Employee ID");
//
//            Company storedCompany = companyService.getCompanyByID(Long.parseLong(CompanyID));
//
//            if(storedCompany == null)
//                throw new InvalidCompanyDataException("Company wasn't found");
//
//            Employee storedEmployee = companyService.getEmployeeFromCompany(Long.parseLong(EmployeeID),storedCompany);
//
//            if(storedEmployee == null)
//                throw new InvalidCompanyDataException("Employee wasn't found");
//
//            return storedEmployee;
//        },toJson());
//
//        exception(InvalidCompanyDataException.class, (e, req, res) -> {
//            res.status(400);
//            res.body(toJson(new GenericErrorMessage(e.getMessage())));
//        });
//
//        exception(JsonSyntaxException.class, (e, req, res) -> {
//            res.status(400);
//            res.body(toJson(new GenericErrorMessage("Error while parsing Json, please verify the Json for errors")));
//        });
//



    }

}

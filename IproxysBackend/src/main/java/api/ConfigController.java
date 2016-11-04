package api;

import JsonParser.CustomGson;
import api.common.Authorize;
import api.common.BaseJsonController;
import api.common.RequestMethod;
import api.common.RequiresAuthorization;
import exceptions.GenericException;
import models.Config;
import models.JsonConfig;
import models.UserRoles;
import persistence.dao.ConfigDao;

import static app.ResponseManager.toJson;
import static spark.Spark.get;
import static spark.Spark.put;

@RequiresAuthorization(allowedRoles = {
        @Authorize(roles = {UserRoles.Admin}, method = RequestMethod.GET, route = "/config"),
        @Authorize(roles = {UserRoles.Admin}, method = RequestMethod.PUT, route = "/config")
})
public class ConfigController extends BaseJsonController {

    public ConfigController() {
        super();

        get("/config", (request, response) -> {
//            return ConfigDao.get();
            Config dataConf = ConfigDao.get();
            JsonConfig newConf = new JsonConfig();

            newConf.setBandwidth(dataConf.getBandwidth());
            newConf.setMaxBandwidthPerUser(dataConf.getMaxBandwidthPerUser());
            newConf.setTempTimeDuration(dataConf.getTempTimeDuration());
            return newConf;
        }, toJson());


        put("/config", (request, response) -> {


            JsonConfig recievedConfig = CustomGson.Gson().fromJson(request.body(), JsonConfig.class);

            if (recievedConfig == null)
                throw new GenericException("Invalid payload");

            ConfigDao.update(recievedConfig);
            return recievedConfig;

        }, toJson());
//
//
//        delete("/user/:id", (request, response) -> {
//
//            String revicedID = request.params(":id");
//
//            if (revicedID == null || revicedID.isEmpty())
//                throw new GenericException("User doesn't exists");
//
//
//            User toDeleteUser = userService.findById(revicedID);
//
//            if (toDeleteUser == null) {
//                throw new GenericException("User doesn't exists");
//            }
//
//            User currentUser = userService.findById(AuthorizationService.getInstance().getDataFromRequest(request).getBody().get("id", String.class));
//            if (currentUser.getId().toHexString().equals(toDeleteUser.getId().toHexString())) {
//                throw new UnAuthorizedException("User cannot delete itself");
//            }
//
//            if (currentUser.getRole() == UserRoles.Distribuitor && toDeleteUser.getRole() != UserRoles.SellingPoint) {
//                throw new UnAuthorizedException("This user can only remove SellingPoint users");
//            }
//
//            if (currentUser.getRole() == UserRoles.Distribuitor && !currentUser.getId().toHexString().equals(toDeleteUser.getCreatedBy().toHexString())) {
//                throw new UnAuthorizedException("This cannot delete the requested user");
//            }
//
//            Mongo.getDataStore().delete(toDeleteUser);
//
//            return "";
//
//        });
//
//        put("/user/:id", (request, response) -> {
//
//            String revicedID = request.params(":id");
//
//            if (revicedID == null || revicedID.isEmpty())
//                throw new GenericException("User doesn't exists");
//
//            User recievedUser = CustomGson.Gson().fromJson(request.body(), User.class);
//
//            if (recievedUser == null)
//                throw new GenericException("Invalid payload");
//
//            if (!recievedUser.isValid())
//                throw new GenericException(recievedUser.getErrorMessage());
//
//            User userToUpdate = userService.findById(revicedID);
//
//            if (userToUpdate == null) {
//                throw new GenericException("User doesn't exists");
//            }
//
//            if (!recievedUser.getEmail().equals(userToUpdate.getEmail()) && userService.findByEmail(recievedUser.getEmail()).size() != 0) {
//                throw new GenericException("Email already exists");
//            }
//
//            if (!recievedUser.getUsername().equals(userToUpdate.getUsername()) && userService.findByUsername(recievedUser.getUsername()).size() != 0) {
//                throw new GenericException("Username already exists");
//            }
//
//            userToUpdate.setUsername(recievedUser.getUsername());
//            userToUpdate.setFirstName(recievedUser.getFirstName());
//            userToUpdate.setLastName(recievedUser.getLastName());
//            userToUpdate.setEmail(recievedUser.getEmail());
//
//            User currentUser = userService.findById(AuthorizationService.getInstance().getDataFromRequest(request).getBody().get("id", String.class));
//
//            if (currentUser.getRole() == UserRoles.Distribuitor && userToUpdate.getRole() != UserRoles.SellingPoint) {
//                throw new UnAuthorizedException("This user can only update SellingPoint users");
//            }
//
//            if (currentUser.getRole() == UserRoles.Distribuitor && !currentUser.getId().toHexString().equals(userToUpdate.getCreatedBy().toHexString())) {
//                throw new UnAuthorizedException("This cannot update the requested user");
//            }
//
//            Mongo.getDataStore().save(userToUpdate);
//
//            return userToUpdate;
//
//        }, toJson());

    }

}

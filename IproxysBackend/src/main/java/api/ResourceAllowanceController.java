package api;

import JsonParser.CustomGson;
import PersistenceData.TemporaryBlockedEntity;
import PersistenceData.UnblockableEntity;
import api.common.Authorize;
import api.common.BaseJsonController;
import api.common.RequestMethod;
import api.common.RequiresAuthorization;
import exceptions.GenericException;
import models.ResourceAllowance;
import models.UserRoles;
import persistence.dao.UnblockableEntityDao;
import services.UserService;

import java.util.ArrayList;
import java.util.List;

import static app.ResponseManager.toJson;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;

@RequiresAuthorization(allowedRoles = {
        @Authorize(roles = {UserRoles.Admin, UserRoles.Distribuitor}, method = RequestMethod.GET, route = "/resource-allowance"),
        @Authorize(roles = {UserRoles.Admin, UserRoles.Distribuitor}, method = RequestMethod.POST, route = "/resource-allowance"),
        @Authorize(roles = {UserRoles.Admin, UserRoles.Distribuitor}, method = RequestMethod.DELETE, route = "/resource-allowance/:id"),
        @Authorize(roles = {UserRoles.Admin, UserRoles.Distribuitor}, method = RequestMethod.PUT, route = "/user/:id"),

})
public class ResourceAllowanceController extends BaseJsonController {

    public ResourceAllowanceController() {
        super();

        get("/resource-allowance", (request, response) -> {

            List<ResourceAllowance> resourceAllowance = new ArrayList<>();

            List<UnblockableEntity> unblockableEntities = UnblockableEntityDao.findByAll();

            for(UnblockableEntity unblockableEntity: unblockableEntities){
                ResourceAllowance  resource = new ResourceAllowance();
                resource.setBlockedPort(unblockableEntity.getBlockedPort());
                resource.setIdentifier(unblockableEntity.getIdentifier()-1);
                resource.setProtocol(unblockableEntity.getProtocol());
                resource.setBlockedDomain(unblockableEntity.getBlockedDomain());
                resource.setBlockedIP(unblockableEntity.getBlockedIP());
                resource.setId(unblockableEntity.getId());
                resourceAllowance.add(resource);
            }
            return resourceAllowance;
        }, toJson());


        post("/resource-allowance", (request, response) -> {

            ResourceAllowance resourceAllowance = CustomGson.Gson().fromJson(request.body(), ResourceAllowance.class);

            if (resourceAllowance == null)
                throw new GenericException("Invalid payload");

            if (!resourceAllowance.isValid())
                throw new GenericException(resourceAllowance.getErrorMessage());

            UnblockableEntity unblockableEntity = new UnblockableEntity();
            switch (resourceAllowance.getIdentifier()){

                case TemporaryBlockedEntity.BLOCK_IP:
                    if(resourceAllowance.getBlockedIP() == null || resourceAllowance.getBlockedIP().isEmpty()){
                        throw new GenericException("Invalid payload: blockIP");
                    }
                    unblockableEntity.setIdentifier(resourceAllowance.getIdentifier());
                    unblockableEntity.setBlockedIP(resourceAllowance.getBlockedIP());
                    unblockableEntity.save();
                    break;
                case TemporaryBlockedEntity.BLOCK_IP_AND_PORT:
                    if(resourceAllowance.getBlockedIP() == null || resourceAllowance.getBlockedIP().isEmpty()){
                        throw new GenericException("Invalid payload:  blockedIP");
                    }
                    /**
                     * hay que ver klk con el default value
                     */
                    if(resourceAllowance.getBlockedPort() == 0){
                        throw new GenericException("Invalid payload:  blockedPort");
                    }
                    if(resourceAllowance.getProtocol() != 17 && resourceAllowance.getProtocol() != 6 ){
                        throw new GenericException("Invalid payload: protocol");
                    }
                    unblockableEntity.setIdentifier(resourceAllowance.getIdentifier());
                    unblockableEntity.setBlockedIP(resourceAllowance.getBlockedIP());
                    unblockableEntity.setBlockedPort(resourceAllowance.getBlockedPort());
                    unblockableEntity.setProtocol(resourceAllowance.getProtocol());
                    unblockableEntity.save();
                    break;
                case TemporaryBlockedEntity.BLOCK_PORT:

                    if(resourceAllowance.getBlockedPort() == 0){
                        throw new GenericException("Invalid payload:  blockedPort");
                    }
                    if(resourceAllowance.getProtocol() != 17 && resourceAllowance.getProtocol() != 6 ){
                        throw new GenericException("Invalid payload: protocol");
                    }
                    unblockableEntity.setIdentifier(resourceAllowance.getIdentifier());
                    unblockableEntity.setBlockedPort(resourceAllowance.getBlockedPort());
                    unblockableEntity.setProtocol(resourceAllowance.getProtocol());
                    unblockableEntity.save();
                    break;
                case TemporaryBlockedEntity.BLOCK_HTTP_DOMAIN_TO_IP:
                    if(resourceAllowance.getBlockedIP() == null || resourceAllowance.getBlockedIP().isEmpty()){
                        throw new GenericException("Invalid payload:  blockedIP");
                    }

                    if(resourceAllowance.getBlockedDomain() == null || resourceAllowance.getBlockedDomain().isEmpty()){
                        throw new GenericException("Invalid payload:  blockedDomain");
                    }
                    unblockableEntity.setBlockedDomain(resourceAllowance.getBlockedDomain());
                    unblockableEntity.setIdentifier(resourceAllowance.getIdentifier());
                    unblockableEntity.setBlockedIP(resourceAllowance.getBlockedIP());
                    unblockableEntity.save();
                    break;
                default:
                    throw new GenericException("Invalid payload:  identifier");

            }

            resourceAllowance.setId(unblockableEntity.getId());
            return resourceAllowance;

        }, toJson());


        delete("/resource-allowance/:id", (request, response) -> {

            String revicedID = request.params(":id");

            if (revicedID == null || revicedID.isEmpty())
                throw new GenericException("Resource doesn't exists");


            UnblockableEntity toDelete  = new UnblockableEntity();
            Object o = toDelete.finbByID(Long.parseLong(revicedID));

            if (o == null) {
                throw new GenericException("Resource doesn't exists");
            }

            toDelete = (UnblockableEntity)o;
            toDelete.delete();

            return "";

        });
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

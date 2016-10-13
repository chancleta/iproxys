package api;

import PersistenceData.TemporaryBlockedEntity;
import api.common.Authorize;
import api.common.BaseJsonController;
import api.common.RequestMethod;
import api.common.RequiresAuthorization;
import models.LiveAction;
import models.UserRoles;
import persistence.dao.TemporaryBlockedEntityDao;

import java.util.ArrayList;
import java.util.List;

import static app.ResponseManager.toJson;
import static spark.Spark.get;

@RequiresAuthorization(allowedRoles = {
        @Authorize(roles = {UserRoles.Admin}, method = RequestMethod.GET, route = "/live-actions"),
})
public class LiveActionsController extends BaseJsonController {

    public LiveActionsController() {
        super();

        get("/live-actions", (request, response) -> {

            List<LiveAction> liveActions = new ArrayList<>();
            for (TemporaryBlockedEntity temp : TemporaryBlockedEntityDao.findTemporaryBlockedEntities()) {
                LiveAction liveAction = new LiveAction();
                liveAction.setBlockedDomain(temp.getBlockedDomain());
                liveAction.setBlockedIP(temp.getBlockedIP());
                liveAction.setBlockedOnTimeDate(temp.getBlockedOnTimeDate());
                liveAction.setBlockedPort(temp.getBlockedPort());
                liveAction.setIdentifier(temp.getIdentifier() - 1);
                liveAction.setProtocol(temp.getProtocol());
                liveActions.add(liveAction);
            }
            return liveActions;

        }, toJson());


//        post("/user", (request, response) -> {
//
//
//            User recievedUser = CustomGson.Gson().fromJson(request.body(), User.class);
//
//            if (recievedUser == null)
//                throw new GenericException("Invalid payload");
//
//            if (!recievedUser.isValid())
//                throw new GenericException(recievedUser.getErrorMessage());
//
//            User user = userService.findById(AuthorizationService.getInstance().getDataFromRequest(request).getBody().get("id", String.class));
//
//            if (user.getRole() == UserRoles.Distribuitor && recievedUser.getRole() != UserRoles.SellingPoint) {
//                throw new UnAuthorizedException("This user can only create SellingPoint users");
//            }
//
//            if (userService.findByEmail(recievedUser.getEmail()).size() != 0) {
//                throw new GenericException("Email already exists");
//            }
//
//            if (userService.findByUsername(recievedUser.getUsername()).size() != 0) {
//                throw new GenericException("Username already exists");
//            }
//
//            recievedUser.setPassword(recievedUser.getUsername());
//            Mongo.getDataStore().save(recievedUser);
//
//
////            HtmlMailSender.generateAndSendEmail(recievedUser.getEmail(),recievedUser.getFirstName());
//
//            return recievedUser;
//
//        }, toJson());
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

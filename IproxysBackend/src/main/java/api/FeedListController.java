package api;

import api.common.Authorize;
import api.common.BaseJsonController;
import api.common.RequestMethod;
import api.common.RequiresAuthorization;
import models.UserRoles;
import services.FeedService;


@RequiresAuthorization(allowedRoles = {
    @Authorize(roles = {UserRoles.Admin, UserRoles.Distribuitor, UserRoles.SellingPoint}, method = RequestMethod.GET, route = "/feed"),

})
public class FeedListController extends BaseJsonController {

    public FeedListController(final FeedService feedService) {
        super();
    }
}

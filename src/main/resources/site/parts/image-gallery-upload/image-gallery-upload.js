var portal = require('/lib/xp/portal');
var thymeleaf = require('/lib/xp/thymeleaf');
var upload = require('/lib/upload');
var contentSrv = require('/lib/xp/content');

// Handle GET request
exports.get = handleGet;

function handleGet(req) {
    var me = this;

    function renderView() {
        var view = resolve('image-gallery-upload.html');
        var model = createModel();

        model.url = portal.componentUrl({});

        return {
            body: thymeleaf.render(view, model)
        };
    }

    function createModel() {
        var model = {};
        return model;
    }

    return renderView();
}

exports.post = function (req) {

    var config = portal.getComponent().config;
    var uploadFolderId = config.uploadFolder;
    var site = portal.getSite();

    var uploadFolder = contentSrv.get({
        key: uploadFolderId
    });

    var part = upload.getUploadPart('file');

    upload.createMedia(uploadFolder._path, part);

    return {
        redirect: portal.pageUrl({path: site._path})
    };
};
var portal = require('/lib/xp/portal');
var thymeleaf = require('/lib/xp/thymeleaf');
var upload = require('/lib/upload');

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

    var part = upload.getUploadPart('file');
    var contentId = upload.createMedia('/upload', part);

    return {
        redirect: portal.pageUrl({path: "/gallery"})
    };

};
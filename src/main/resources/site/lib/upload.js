var bean = __.newBean('com.enonic.app.gallery.UploadSupport');

exports.getUploadPart = function (name) {

    return bean.getUploadPart(name);

};

exports.createMedia = function (parentPath, part) {

    return bean.createMedia(parentPath, part);

};

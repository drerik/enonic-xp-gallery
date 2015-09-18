var upload = require('/lib/upload');

exports.get = function (req) {

    return {
        status: 200,
        contentType: 'application/json',
        body: {
            "message": "This upload service only supports POST!"
        }
    }

};

exports.post = function (req) {

    var part = upload.getUploadPart('file');
    var contentId = upload.createMedia('/upload', part);

    return {
        status: 200,
        contentType: 'application/json',
        body: {
            id: contentId,
            size: part.size,
            name: part.name,
            contentType: part.contentType
        }
    }

};

import http from 'k6/http';
import {FormData} from 'https://jslib.k6.io/formdata/0.0.2/index.js';

const binFile = open('/home/garry/dev/workspace/colossus-api/src/utils/logger.ts');

export default function () {
    let filepath = `src/views/file_${Date.now()}.js`;
    var formdata = new FormData();
    formdata.append("file",  http.file(binFile, filepath));

    if(Date.now() % 2 === 0) {
        formdata.append("filepath", 'folder.hash');
    } else {
        formdata.append("filepath", filepath);
    }

    formdata.append("why", "save");
    http.post('http://localhost:8081/workspace-api/fileupload/2', formdata.body(), {
        headers: {
            'Content-Type': 'multipart/form-data; boundary=' + formdata.boundary,
            email: 'garrydias@gmail.com'
        },
    });
}

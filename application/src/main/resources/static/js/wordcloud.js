

const baseUrl = 'http://localhost:8080'
const url = '/v1/file'
const getMapping = '/word/cloud'
const requestParam = '?files=small,big'
reqUrl = baseUrl + url + getMapping + requestParam

const uploadFileForm = document.querySelector("#uploadFileForm");
const fileList = document.querySelector("#file-list");
const fileInput = document.querySelector("#files");

const fakeRequest = (url) => {
    return new Promise((resolve, reject ) => {
        setTimeout(() => {
            resolve();
        }, 5000)
    })
}

uploadFileForm.addEventListener("change", function () {
    console.log(uploadFileForm);
}, false);

uploadFileForm.addEventListener("submit", function (e) {
    console.log(e);
    e.preventDefault();
    axios.get(reqUrl).then((res) => {console.log("Response",res)})
});

fileInput.addEventListener("input", function (e) {
    fileList.innerHTML = "";
    // files is a FileList object (similar to NodeList)
    printFiles();
});

function printFiles() {
        ul = document.createElement('ul');
        ul.setAttribute("id", "container");
        fileList.append(ul);
        count = 0;

        for (let file of fileInput.files) {
            let li = document.createElement('li');
            li.setAttribute("onclick","removeFile(this.innerText)");
            ul.appendChild(li);
            li.innerHTML += file.name;
        }
    }

function removeFile(name) {
    const fileListArr = Array.from(fileInput.files)
    const index = findFile(name);
    fileListArr.splice(index, 1);// here u remove the file

     let container = new DataTransfer();

     fileListArr.forEach(function(file1) {
        container.items.add(file1);
     });

    console.log(container.file);
    console.log(fileListArr);
    fileInput.files = container.file;// 2nd parameter means remove one item only
    console.log(fileInput.files);
}

function remove(element) {
    element.remove();
}

function findFile(name) {
    count = 0;
    for (let file of fileInput.files) {
        if (name == file.name) {
            return count;
        }
        count++;
    }
    return count;
}


const baseUrl = 'http://localhost:8080'
const url = '/v1/file'
const getMapping = '/word/cloud'
const requestParam = '?files=small,big'
reqUrl = baseUrl + url + getMapping + requestParam

const form = document.querySelector("#uploadFileForm");

console.log(reqUrl);

const fakeRequest = (url) => {
    return new Promise((resolve, reject ) => {
        setTimeout(() => {
            resolve();
        }, 5000)
    })
}



form.addEventListener("submit", function (e) {
    console.log(e);
    e.preventDefault();
    axios.get(reqUrl).then((res) => {console.log("Response",res)})
});
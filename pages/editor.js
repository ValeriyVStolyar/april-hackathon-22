import * as data from "./data.js";

document.getElementById("edit__button").addEventListener("click", makeRequest, false);

function makeRequest() {
    let body = [];
    //TODO переписать - когда никогда
    body.push({key: data.greeting.name, value: data.greeting.value});
    body.push({key: data.job.name, value: data.job.value});
    body.push({key: data.works.name, value: data.works.value});
    body.push({key: data.aboutFirst.name, value: data.aboutFirst.value});
    body.push({key: data.aboutSecond.name, value: data.aboutSecond.value});
    body.push({key: data.stack.name, value: data.stack.value});
    body.push({key: data.stackDescription.name, value: data.stackDescription.value});
    body.push({key: data.skillOne.name, value: data.skillOne.value});
    body.push({key: data.skillTwo.name, value: data.skillTwo.value});
    body.push({key: data.skillThree.name, value: data.skillThree.value});
    body.push({key: data.toolsOne.name, value: data.toolsOne.value});
    body.push({key: data.toolsTwo.name, value: data.toolsTwo.value});
    body.push({key: data.toolsThree.name, value: data.toolsThree.value});
    body.push({key: data.skillDescriptionOne.name, value: data.skillDescriptionOne.value});
    body.push({key: data.skillDescriptionTwo.name, value: data.skillDescriptionTwo.value});
    body.push({key: data.skillDescriptionThree.name, value: data.skillDescriptionThree.value});
    body.push({key: data.subtitleOne.name, value: data.subtitleOne.value});
    body.push({key: data.paragraphOne.name, value: data.paragraphOne.value});
    body.push({key: data.Dribbble.name, value: data.Dribbble.value})
    body.push({key: data.Behance.name, value: data.Behance.value})
    body.push({key: data.CodePen.name, value: data.CodePen.value})
    body.push({key: data.GitHub.name, value: data.GitHub.value})
    body.push({key: data.Medium.name, value: data.Medium.value})
    body.push({key: data.LinkedIn.name, value: data.LinkedIn.value})
    body.push({key: data.Facebook.name, value: data.Facebook.value})
    body.push({key: data.Instagram.name, value: data.Instagram.value})
    body.push({key: data.Twitter.name, value: data.Twitter.value})
    body.push({key: data.mail.name, value: data.mail.value})
    body.push({key: data.tel.name, value: data.tel.value})

    async function postData(url = '', data = []) {
        // Default options are marked with *
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                //'Content-Type': 'application/json'
                // 'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: JSON.stringify(data)
        });
        return await response;
    }

    postData("http://localhost:8080/files/downloadFilledTemplate?userName=" + localStorage.getItem('login'), body)
        .then(response => response.text())
        .then((response) => {
            window.location.href = response;
        })

    //Как же я люблю промисы:3
    console.log(body);
}
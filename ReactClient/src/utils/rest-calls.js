import {FESTIVAL_PERFORMANCES_BASE_URL} from './consts';

function status(response) {
    console.log('Response status: ' + response.status);
    if (response.status >= 200 && response.status < 300) {
        return Promise.resolve(response)
    } else {
        return Promise.reject(new Error(response.statusText))
    }
}

function json(response) {
    return response.json()
}

export function GetAllPerformances(){
    const headers = new Headers();
    headers.append('Accept', 'application/json');
    const myInit = {
        method: 'GET',
        headers: headers,
        mode: 'cors'
    };
    const request = new Request(FESTIVAL_PERFORMANCES_BASE_URL, myInit);
    console.log('Before fetch for ' + FESTIVAL_PERFORMANCES_BASE_URL)
    return fetch(request)
        .then(status)
        .then(json)
        .then(data=> {
            console.log('Request succeeded with JSON response', data);
            return data;
        }).catch(error=>{
            console.log('Error: ', error);
            return Promise.reject(error);
        });
}

export function DeletePerformance(id){
    console.log('Before de fetch delete.')
    const myHeaders = new Headers();
    myHeaders.append("Accept", "application/json");
    const antet = {
        method: 'DELETE',
        headers: myHeaders,
        mode: 'cors'
    };
    const performanceDelUrl = FESTIVAL_PERFORMANCES_BASE_URL + '/' + id;
    console.log('URL for delete: ' + performanceDelUrl)
    return fetch(performanceDelUrl, antet)
        .then(status)
        .then(response=>{
            console.log('Delete status: ' + response.status);
            return response.text();
        }).catch(e=>{
            console.log('Error: ' + e);
            return Promise.reject(e);
        });
}

export function UpdatePerformance(performance){
    console.log('Before de fetch update.')
    const myHeaders = new Headers();
    myHeaders.append("Accept", "application/json");
    myHeaders.append("Content-Type","application/json");
    const antet = {
        method: 'PUT',
        headers: myHeaders,
        mode: 'cors',
        body: JSON.stringify(performance)
    };
    const performanceUpdateUrl = FESTIVAL_PERFORMANCES_BASE_URL + '/' + performance.id;
    console.log('URL for update: ' + performanceUpdateUrl)
    return fetch(performanceUpdateUrl, antet)
        .then(status)
        .then(response=>{
            console.log('Update status: ' + response.status);
            return response.text();
        }).catch(e=>{
            console.log('Error: ' + e);
            return Promise.reject(e);
        });
}

export function CreatePerformance(performance){
    console.log('Before post fetch: ' + JSON.stringify(performance));
    const myHeaders = new Headers();
    myHeaders.append("Accept", "application/json");
    myHeaders.append("Content-Type","application/json");
    const antet = {
        method: 'POST',
        headers: myHeaders,
        mode: 'cors',
        body: JSON.stringify(performance)
    };
    console.log(antet);
    return fetch(FESTIVAL_PERFORMANCES_BASE_URL, antet)
        .then(status)
        .then(response=>{
            return response.text();
        }).catch( error => {
            console.log('Error: ', error);
            return Promise.reject(error);
        });
}


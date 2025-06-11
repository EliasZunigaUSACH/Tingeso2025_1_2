import httpClient from "../http-common";

const getAll = () => {
    return httpClient.get('/client-service/api/v1/clients/');
}

const create = data => {
    return httpClient.post("/client-service/api/v1/clients/", data);
}

const get = id => {
    return httpClient.get(`/client-service/api/v1/clients/${id}`);
}

const update = data => {
    return httpClient.put('/client-service/api/v1/clients/', data);
}

const remove = id => {
    return httpClient.delete(`/client-service/api/v1/clients/${id}`);
}
export default { getAll, create, get, update, remove };
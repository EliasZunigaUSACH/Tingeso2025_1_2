import httpClient from '../http-common';

const getAll = () => {
    return httpClient.get('/clients');
};

const get = id => {
    return httpClient.get(`/clients/${id}`);
}

const create = data => {
    return httpClient.post('/clients', data);
}

const update = (id, data) => {
    return httpClient.put(`/clients/${id}`, data);
}

const remove = id => {
    return httpClient.delete(`/clients/${id}`);
}

export default { getAll, get, create, update, remove };
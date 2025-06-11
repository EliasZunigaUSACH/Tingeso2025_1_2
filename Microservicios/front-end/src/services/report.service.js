import httpClient from '../http-common';

const getById = (id) => {
  return httpClient.get(`/report-service/api/v1/report/${id}`);
}

const getAll = () => {
    return httpClient.get('/report-service/api/v1/report');
}

const create = (data) => {
  return httpClient.post('/report-service/api/v1/report', data);
}

const remove = (id) => {   
    return httpClient.delete(`report-service/api/v1/report/${id}`);
}

export default { getById, getAll, create, remove };
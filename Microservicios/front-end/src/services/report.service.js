import httpClient from '../http-common';

const getById = (id) => {
  return httpClient.get(`/report-service/api/v1/reports/${id}`);
}

const getAll = () => {
    return httpClient.get('/report-service/api/v1/reports');
}

const create = (data) => {
  return httpClient.post('/report-service/api/v1/reports', data);
}

const remove = (id) => {   
    return httpClient.delete(`report-service/api/v1/reports/${id}`);
}

export default { getById, getAll, create, remove };
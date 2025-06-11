import httpClient from '../http-common';

const getAll = () => {
  return httpClient.get('/rack-service/api/v1/rack');
}

const getById = (id) => {
  return httpClient.get(`/rack-service/api/v1/rack/${id}`);
}

const getByYearMonth = (year, month) => {
  return httpClient.get(`/rack-service/api/v1/rack/year/${year}/month/${month}`);
}

const create = (data) => {
  return httpClient.post('/rack-service/api/v1/rack', data);
}

const update = (id, data) => {
  return httpClient.put(`/rack-service/api/v1/rack/${id}`, data);
}

export default { getAll, getById, getByYearMonth, create, update };
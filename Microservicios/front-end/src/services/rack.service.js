import httpClient from '../http-common';

const getAll = () => {
  return httpClient.get('/rack-service/api/v1/racks');
}

const getById = (id) => {
  return httpClient.get(`/rack-service/api/v1/racks/${id}`);
}

const getByYearMonth = (year, month) => {
  return httpClient.get(`/rack-service/api/v1/racks/year/${year}/month/${month}`);
}

const create = (data) => {
  return httpClient.post('/rack-service/api/v1/racks', data);
}

const update = (id, data) => {
  return httpClient.put(`/rack-service/api/v1/racks/${id}`, data);
}

export default { getAll, getById, getByYearMonth, create, update };
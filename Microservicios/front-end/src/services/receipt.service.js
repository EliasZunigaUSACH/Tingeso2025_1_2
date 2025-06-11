import httpClient from "../http-common";

const getById = (id) => {
  return httpClient.get(`/receipt-service/api/v1/receipt/${id}`);
}

const create = (data) => {
  return httpClient.post("/receipt-service/api/v1/receipt", data);
}

export default { getById, create };
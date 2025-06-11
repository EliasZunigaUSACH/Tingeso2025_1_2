import httpClient from "../http-common";

const getById = (id) => {
  return httpClient.get(`/receipt-service/api/v1/receipts/${id}`);
}

const create = (data) => {
  return httpClient.post("/receipt-service/api/v1/receipts", data);
}

const remove = (id) => {
  return httpClient.remove(`/receipt-service/api/v1/receipts/${id}`);
}

export default { getById, create , remove };
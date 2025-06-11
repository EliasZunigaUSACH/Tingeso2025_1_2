import httpClient from "../http-common";

const getAll = () => {
    return httpClient.get('/reservation-service/api/v1/reservations/');
}

const create = data => {
    return httpClient.post("/reservation-service/api/v1/reservations/", data);
}

const get = id => {
    return httpClient.get(`/reservation-service/api/v1/reservations/${id}`);
}

const update = data => {
    return httpClient.put('/reservation-service/api/v1/reservations/', data);
}

const remove = id => {
    return httpClient.delete(`/reservation-service/api/v1/reservations/${id}`);
}
export default { getAll, create, get, update, remove };
import axios from "axios";
 
const BackendServer = import.meta.env.VITE_PROJECT_BACKEND_SERVER;
const BackendPort = import.meta.env.VITE_PROJECT_BACKEND_PORT;

console.log(BackendServer)
console.log(BackendPort)

export default axios.create({
    baseURL: `http://${BackendServer}:${BackendPort}`,
    headers: {
        'Content-Type': 'application/json'
    }
});

/*
const httpClient = axios.create({
    baseURL: `http://${import.meta.env.VITE_PROJECT_BACKEND_SERVER}:${import.meta.env.VITE_PROJECT_BACKEND_PORT}`, // Default base URL, can be overridden
    headers: {
        'Content-Type': 'application/json',
    }
});

httpClient.interceptors.request.use(
    (config) => {
        console.log(`${config.method?.toUpperCase()} ${config.baseURL}${config.url}`);
        return config;
    },
    (error) => {
        console.error("Error en request:", error);
        return Promise.reject(error);
    }
);

httpClient.interceptors.response.use(
    (response) => {
    console.log(`${response.status} ${response.config.url}`);
    return response;
  },
    (error) => {
        console.error(`${error.response?.status || 'Network Error'} ${error.config?.url}`);
        return Promise.reject(error);
    }
);

export default httpClient;*/
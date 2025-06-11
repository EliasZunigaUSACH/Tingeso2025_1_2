import axios from "axios";
 /*
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
*/

const httpClient = axios.create({
    baseURL: `http://${import.meta.env.VITE_PROJECT_BACKEND_SERVER}:${import.meta.env.VITE_PROJECT_BACKEND_PORT}`, // Default base URL, can be overridden
    headers: {
        'Content-Type': 'application/json'
    }
});

httpClient.interceptors.request.use(
    config => {
        // Add any custom logic before sending the request
        // For example, you can add authentication tokens here
        const token = localStorage.getItem('token'); // Assuming you store the token in localStorage
        if (token) {
            config.headers['Authorization'] = `Bearer ${token}`;
        }
        return config;
    },
    error => {
        return Promise.reject(error);
    }
);

httpClient.interceptors.response.use(
    response => {
        // Handle the response data
        return response;
    },
    error => {
        // Handle errors globally
        if (error.response && error.response.status === 401) {
            // Redirect to login or show an error message
            console.error("Unauthorized access - redirecting to login");
            // Optionally, you can redirect to a login page here
        }
        return Promise.reject(error);
    }
);

export default httpClient;
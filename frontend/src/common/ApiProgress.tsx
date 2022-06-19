import axios, { AxiosRequestConfig } from 'axios';
import { useEffect, useState } from 'react';

export const useApiProgress = (apiMethod:string ,path: string) => {

    const [pendingApiCall, setPendingApiCall] = useState(false);
    const PATHPREFIX = "/api/1.0/";
    const updateState = (method:string ,url: string, status: boolean): void => {
        if (url.startsWith(path) && method === apiMethod) {
            setPendingApiCall(status);
        }
    }

    useEffect(() => {
        const requestId = axios.interceptors.request.use((request: AxiosRequestConfig) => {
            const {method,url} = request;
            updateState(method!,url!.replace(PATHPREFIX,"")!,true);
            const urls = url!.indexOf(PATHPREFIX) > -1 ? url : PATHPREFIX + request.url;
            request.url = urls;
            return request;
        });

        const responseId = axios.interceptors.response.use((response) => {
            const {method,url} = response.config;
            const parsedUrl = url!.replace(PATHPREFIX,"");
            updateState(method!,parsedUrl!,false);
            return response;

        }, (error) => {
            const {method,url} = error.config;
            const parsedUrl = url!.replace(PATHPREFIX,"");
            updateState(method!,parsedUrl!,false);            
            throw error;
        });
        return () => {
            axios.interceptors.request.eject(requestId);
            axios.interceptors.response.eject(responseId);
        }

    },[path]);

    return pendingApiCall;
}







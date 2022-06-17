import { createSecureServer } from 'http2';
import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useParams } from 'react-router-dom';
import { getUser } from '../api/ApiCalls';
import { useApiProgress } from '../common/ApiProgress';
import ProfileCard from '../component/ProfileCard';
import Spinner from '../component/Spinner';

const UserPage = (props: any) => {

    const [user, setUser] = useState({});
    const [apiError, setApiError] = useState(false);
    const { username } = useParams() as any;
    const { t } = useTranslation();
    const pendingApiCall = useApiProgress("get",`/api/1.0/users/${username}`);

    useEffect(() => {
        const loadUser = async () => {
            try {
                const response = await getUser(username);
                setUser(response.data);
            } catch (error) {
                setApiError(true);
            }

        }
        loadUser();
    }, [username]);

    useEffect(()=> {
        setApiError(false);
    },[user]);


    if (pendingApiCall) {
        return (
            <div className='text-center'>
                <Spinner/>
            </div>
        )
    }

    if (apiError) {
        return (
            <div className="container alert alert-danger text-center" role="alert">
                {t("User not found")}
            </div>
        )
    }

    return (
        <div>
            <ProfileCard {...props} user={user} />
        </div>
    );
};

export default UserPage;
import { createSecureServer } from "http2";
import React, { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { useParams } from "react-router-dom";
import { getUser } from "../api/ApiCalls";
import { useApiProgress } from "../common/ApiProgress";
import FeedItem from "../component/FeedItem";
import FeedList from "../component/FeedList";
import ProfileCard from "../component/ProfileCard";
import Spinner from "../component/Spinner";

const UserPage = (props: any) => {
  const [user, setUser] = useState({});
  const [apiError, setApiError] = useState(false);
  const { username } = useParams() as any;
  const { t } = useTranslation();
  const pendingApiCall = useApiProgress("get", `users/${username}`);

  useEffect(() => {
    const loadUser = async () => {
      try {
        const response = await getUser(username);
        setUser(response.data);
      } catch (error) {
        setApiError(true);
      }
    };
    loadUser();
  }, [username]);

  useEffect(() => {
    setApiError(false);
  }, [user]);

  if (pendingApiCall) {
    return (
      <div className="text-center">
        <Spinner />
      </div>
    );
  }

  if (apiError) {
    return (
      <div className="container alert alert-danger text-center" role="alert">
        {t("User not found")}
      </div>
    );
  }

  return (
    <div className="container">
      <div className="row">
        <div className="col-5">
          <ProfileCard user={user} />
        </div>
        <div className="col-7">
          <FeedList />
        </div>
      </div>
    </div>
  );
};

export default UserPage;

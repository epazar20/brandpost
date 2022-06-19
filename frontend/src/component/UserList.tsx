import { isContentEditable } from "@testing-library/user-event/dist/utils";
import React, { Component, useEffect, useState } from "react";
import { useTranslation, withTranslation } from "react-i18next";
import { useLocation, useParams } from "react-router-dom";
import { getUsers } from "../api/ApiCalls";
import { useApiProgress } from "../common/ApiProgress";
import Spinner from "./Spinner";
import UserItem from "./UserItem";

const UserList = (props: any) => {
  const [usersData, setUsersData] = useState({});
  const [errorLoad, setErrorLoad] = useState(false);
  const pendingApiCall = useApiProgress("get", "users?page");
  let location = useLocation();
  const { content: users, first, last, number } = usersData as any;
  const {type} = props;
  useEffect(() => {
    loadUsers();
  }, [location]);

  useEffect(() => {
    setErrorLoad(false);
  }, [usersData]);

  const { t } = useTranslation();

  const onClickNext = () => {
    loadUsers(number + 1);
  };

  const onClickPrevious = () => {
    loadUsers(number - 1);
  };

  const loadUsers = async (page: number = 0) => {
    try {
      const response = await getUsers(type,page);
      setUsersData(response.data);
    } catch (error) {
      setErrorLoad(true);
    }
  };

  return (
    <div className="card">
      <h3 className="card-header text-center">{t("Users" + type)}</h3>
      <div className="list-group-flush">
        {users &&
          users.length > 0 &&
          users.map((user: any) => {
            return <UserItem key={user.username} user={user} />;
          })}
      </div>
      {pendingApiCall ? (
        <Spinner />
      ) : (
        <div>
          {first === false && (
            <button className="btn btn-sm btn-light" onClick={onClickPrevious}>
              {t("Previous")}
            </button>
          )}
          {last === false && (
            <button
              className="btn btn-sm btn-light float-end"
              onClick={onClickNext}
            >
              {t("Next")}
            </button>
          )}
        </div>
      )}
      {errorLoad && (
        <div className="alert alert-danger text-center" role="alert">
          {t("Load Failure")}
        </div>
      )}
    </div>
  );
};

export default UserList;

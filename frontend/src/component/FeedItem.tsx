import React, { useState } from "react";
import { useTranslation } from "react-i18next";
import { useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { format } from "timeago.js";
import { deleteFeed } from "../api/ApiCalls";
import { useApiProgress } from "../common/ApiProgress";
import Modal from "./Modal";
import ProfileImage from "./ProfileImage";

const FeedItem = (props: any) => {
  const { feed, onDeleteFeedCallBack } = props;
  const { user, createddate, content, attachment, id } = feed;
  const { image, username, displayname } = user;
  const { i18n, t } = useTranslation();
  const formatted = format(createddate, i18n.language);
  const pendingApiCall = useApiProgress("delete", `feeds/${id}`);
  const { logginuser } = useSelector((store: any) => {
    return { logginuser: store.username };
  });
  const [visibleModal, setVisibleModal] = useState(false);

  const ondeleteFeed = async () => {
    await deleteFeed(id);
    onDeleteFeedCallBack(id);
    setVisibleModal(false);
  };

  const ondeleteModal = () => {
    setVisibleModal(true);
  };

  const isLoginUser = logginuser === username;

  return (
    <div className="card p-1">
      <div className="d-flex">
        <ProfileImage imageDb={image} width="32" height="32" />
        <div className="flex-fill m-auto ps-3">
          <Link to={`/user/${username}`} className="text-dark flex-row">
            <div className="d-flex justify-content-between">
              <h6 className="d-inline">{displayname}</h6>
              <div className="float-right">{formatted}</div>
            </div>
          </Link>
        </div>
        {isLoginUser && (
          <button
            onClick={ondeleteModal}
            className="btn button-delete-link btn-sm"
          >
            <i className="material-icons">delete</i>
          </button>
        )}
      </div>
      <div className="ps-5">{content}</div>
      {attachment && attachment.name && (
        <div className="pl-5">
          {attachment.fileType.startsWith("image") && (
            <img
              className="img-fluid"
              src={"images/attachments/" + attachment.name}
              alt={content}
            />
          )}
          {!attachment.fileType.startsWith("image") && (
            <strong>Feed has unknown attachment</strong>
          )}
        </div>
      )}
      {visibleModal && (
        <Modal
          visible={visibleModal}
          onClickCancel={() => {
            setVisibleModal(false);
          }}
          message={
            <div>
              <strong>{t("Are you sure to delete feed?")}</strong>
              <div>{content}</div>
            </div>
          }
          onClickOk={ondeleteFeed}
          pendingApiCall={pendingApiCall}
          title={t("Delete feed")}
          okButton={t("Delete feed")}
        />
      )}
    </div>
  );
};

export default FeedItem;

import React from "react";
import { useTranslation } from "react-i18next";
import ButtonWithLoading from "./ButtonWithLoading";

const Modal = (props: any) => {
  const {
    visible,
    onClickCancel,
    message,
    onClickOk,
    pendingApiCall,
    title,
    okButton,
  } = props;
  const { t } = useTranslation();

  let className = "modal fade";
  if (visible) {
    className += " show d-block";
  }

  return (
    <div className={className} style={{ backgroundColor: "#000000b0" }}>
      <div className="modal-dialog">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title">{title}</h5>
          </div>
          <div className="modal-body">{message}</div>
          <div className="modal-footer">
            <button
              className="btn btn-secondary"
              disabled={pendingApiCall}
              onClick={onClickCancel}
            >
              {t("Cancel")}
            </button>
            <ButtonWithLoading
              className="btn btn-danger"
              onClick={onClickOk}
              pendingApiCall={pendingApiCall}
              disabled={pendingApiCall}
              text={okButton}
            />
          </div>
        </div>
      </div>
    </div>
  );
};

export default Modal;

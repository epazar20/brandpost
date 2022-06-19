import { Icon } from "@iconify/react";
import { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { useDispatch, useSelector } from "react-redux";
import { useHistory, useParams } from "react-router-dom";
import { deleteUser, updateUser } from "../api/ApiCalls";
import { useApiProgress } from "../common/ApiProgress";
import { logoutAction, updateSuccess } from "../redux/authAction";
import Input from "./Input";
import Modal from "./Modal";
import ProfileImage from "./ProfileImage";

const ProfileCard = (props: any) => {
  // const context = useContext(Authentication);
  // const {state} = context as any ;
  // const { username } = state;
  const [editMode, setEditMode] = useState(false);
  const routerParams = useParams();
  const userPath: string = (routerParams as any).username;
  const { t } = useTranslation();
  const [users, setUsers] = useState({});
  const { username, displayname, image } = users as any;
  const [editable, setEditable] = useState(false);
  const [updatedName, setUpdatedName] = useState(displayname);
  const pendingApiCall = useApiProgress("put", `users/${username}`);
  const pendingApiCallDelete = useApiProgress("delete", `users/${username}`);
  const { loginname } = useSelector((store: any) => {
    return { loginname: store.username };
  });
  const [errors, setErrors] = useState({});
  const [file, setFile] = useState("");
  const [visibleModal, setVisibleModal] = useState(false);
  const dispatch = useDispatch();
  const history = useHistory();

  useEffect(() => {
    setUsers({ ...props.user });
  }, [props.user]);

  useEffect(() => {
    setEditable(userPath === loginname);
  }, [userPath, loginname]);

  useEffect(() => {
    setErrors((previous) => ({ ...previous, displayname: undefined }));
  }, [updatedName]);

  useEffect(() => {
    setErrors((previous) => {
      return { ...previous, image: undefined };
    });
  }, [file]);

  const onSaveClick = async () => {
    try {
      const response = await updateUser(userPath, {
        displayname: updatedName,
        image: file.split("base64,")[1],
      });
      setUsers(response.data);
      dispatch(updateSuccess(response.data));
      setEditMode(false);
    } catch (error: any) {
      if (error.response.data.validationerrors) {
        setErrors(error.response.data.validationerrors);
      }
    }
  };

  useEffect(() => {
    if (editMode) setUpdatedName(displayname);
    else {
      setUpdatedName(undefined);
      setFile("");
    }
  }, [editMode, displayname]);

  const onChangeFile = (event: any) => {
    const file = event.target.files[0];
    const fileReader = new FileReader();
    fileReader.onloadend = () => {
      setFile(fileReader!.result!.toString());
    };
    fileReader.readAsDataURL(file);
  };

  const onDeleteUser = async () => {
    await deleteUser(username);
    setVisibleModal(false);
    dispatch(logoutAction());
    history.push("/");

  };

  const onModalDeleteUser = () => {
    setVisibleModal(true);
  };

  const { displayname: displaynameError, image: imageError } = errors as any;
  return (
    <div className="card text-center">
      <div className="card-header">
        <ProfileImage imageDb={image} tempimage={file} width={200} />
      </div>
      <div className="card-body">
        {editMode && (
          <>
            <Input type="file" error={imageError} onChange={onChangeFile} />
            <Input
              label={t("Change Display Name")}
              error={displaynameError}
              defaultValue={displayname}
              onChange={(event: any) => {
                setUpdatedName(event.target.value);
              }}
            />
            <div className="form-inline text-center">
              <button
                className={"btn btn-primary d-inline-flex mt-2"}
                onClick={onSaveClick}
              >
                {
                  <>
                    <Icon className="mt-1 me-1" icon="mdi:content-save" />
                    {t("Save")}
                  </>
                }
                {pendingApiCall && (
                  <span className="spinner-border spinner-border-sm"></span>
                )}
              </button>

              <button
                className="btn btn-secondary d-inline-flex mt-2 ms-2"
                onClick={() => {
                  setEditMode(false);
                }}
              >
                <Icon className="mt-1 me-1" icon="mdi:close-thick" />
                {t("Cancel")}
              </button>
            </div>
          </>
        )}
        {!editMode && (
          <>
            <h4>{displayname + " - " + username}</h4>
            {editable && (
              <>
                <div className="text-center">
                  <button
                    className="btn btn-primary d-inline-flex"
                    onClick={() => {
                      setEditMode(true);
                    }}
                  >
                    <Icon
                      className="text-white m-auto"
                      icon="bi:person-circle"
                    />
                    <span
                      className="iconify mt-1 me-1"
                      data-icon="mdi:pencil"
                    ></span>
                    {t("Edit")}
                  </button>
                  <button
                    className="btn btn-danger d-inline-flex"
                    onClick={onModalDeleteUser}
                  >
                    <Icon className="text-white m-auto" icon="fa:power-off" />
                    <span
                      className="iconify mt-1 me-1"
                      data-icon="mdi:pencil"
                    ></span>
                    {t("Delete My Account")}
                  </button>
                </div>
                {visibleModal && (
                  <Modal
                    visible={visibleModal}
                    onClickCancel={() => {
                      setVisibleModal(false);
                    }}
                    message={
                      <div>
                        <strong>{t("Are you sure to delete your account?")}</strong>
                      </div>
                    }
                    onClickOk={onDeleteUser}
                    pendingApiCall={pendingApiCallDelete}
                    title={t("Delete My Account")}
                    okButton={t("Delete My Account")}
                  />
                )}
              </>
            )}
          </>
        )}
      </div>
    </div>
  );
};

export default ProfileCard;

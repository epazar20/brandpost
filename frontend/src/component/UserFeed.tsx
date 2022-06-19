import { Icon } from "@iconify/react";
import { t } from "i18next";
import React, { useEffect, useRef, useState } from "react";
import { useSelector } from "react-redux";
import { sendFeedApi, uploadFile } from "../api/ApiCalls";
import { useApiProgress } from "../common/ApiProgress";
import AutoUploadImage from "./AutoUploadImage";
import ButtonWithLoading from "./ButtonWithLoading";
import FeedList from "./FeedList";
import Input from "./Input";
import ProfileImage from "./ProfileImage";

const UserFeed = () => {
  const { image } = useSelector((store: any) => {
    return { image: store.image };
  });
  const [focused, setFocused] = useState(false);
  const [feed, setFeed] = useState("");
  const pendingApiCall = useApiProgress("post", "feeds");
  const pendingApiCallAttach = useApiProgress("post", "attachments");
  const [errors, setErrors] = useState({});
  const [file, setFile] = useState("");
  const [fileId, setFileId] = useState("");

  useEffect(() => {
    if (!focused) {
      setFeed('');
      setErrors({});
      setFile("");
      setFileId("");

    }
  }, [focused]);

  useEffect(() => {
    setErrors({});
  }, [feed]);

  const sendFeed = async () => {
    try {
      const data = {
        content: feed,
        attachmentid: fileId
      };
      const response = await sendFeedApi(data);
      setFocused(false);
    } catch (error: any) {
      if (error.response.data.validationerrors) {
        setErrors(error.response.data.validationerrors);
      }
    }
  };

  const onChangeFile = (event: any) => {

    const file = event.target.files[0];
    const fileReader = new FileReader();
    fileReader.onloadend = () => {
        setFile(fileReader!.result!.toString());
        uploadFiles(file);
    }
    fileReader.readAsDataURL(file);
}

const uploadFiles = async (file:any) => {
    const data = new FormData();
    data.append("file",file);
    try {
        const res :any= await uploadFile(data);
        console.log(res);
        setFileId((res.data as any).id)
        console.log(fileId);

    } catch (error) {
        
    }
}

  const { content, image:imageError } = errors as any;
  let textAreaClass = "form-control";
  if (content) {
    textAreaClass += " is-invalid";
  }

  return (
    <>
      <div className="card flex-row ">
        <div className="p-2">
          <ProfileImage imageDb={image} width="32" height="32" />
        </div>
        <div className="flex-fill p-2">
          <textarea
            className={textAreaClass}
            rows={focused ? 3 : 1}
            onFocus={() => setFocused(true)}
            onChange={(event) => setFeed(event.target.value)}
            value={feed}
          />
          <div className="invalid-feedback">{content}</div>
          {focused && (
            <>
              { !file && <Input type="file" error={imageError} onChange={onChangeFile} />}
              {file && (
                <AutoUploadImage
                  image={file}
                  uploading={pendingApiCallAttach}
                />
              )}
              <div className="d-flex flex-row-reverse p-2">
                <ButtonWithLoading
                  className="btn btn-light d-inline-flex ms-1 m-auto"
                  onClick={() => setFocused(false)}
                  disabled={pendingApiCall} // || pendingFileUpload}
                  text={
                    <>
                      <Icon className="text-danger" icon="close" />
                      {t("Cancel")}
                    </>
                  }
                />
                <ButtonWithLoading
                  className="btn btn-primary m-auto"
                  onClick={sendFeed}
                  text={t("Send")}
                  pendingApiCall={pendingApiCall}
                  disabled={pendingApiCall} // || pendingFileUpload}
                />
              </div>
            </>
          )}
        </div>
      </div>

      {/*  <div className="card p-1 flex-row" ref={feedRef}>
        <div className="m-auto">
          <ProfileImage imageDb={image} width={32} />
        </div>
        <div className="card-body">
          <textarea
            className={content ? "form-control is-invalid" : "form-control"}
            rows={focused ? 3 : 1}
            value={feed}
            onFocus={() => {
              setFocused(true);
            }}
            //onBlur={() => { setFocused(false) }}
            onChange={(event: any) => {
              setFeed(event.target.value);
            }}
          ></textarea>
          <div className="invalid-feedback">{content}</div>
        </div>

        {focused && (
          <div className="m-auto">
            <ButtonWithLoading
              onClick={sendFeed}
              disabled={pendingApiCall}
              pendingApiCall={pendingApiCall}
              text={t("Send")}
            />
          </div>
        )}
      </div> */}
    </>
  );
};

export default UserFeed;

import { t } from 'i18next';
import React, { useEffect, useRef, useState } from 'react';
import { useSelector } from 'react-redux';
import { sendFeedApi } from '../api/ApiCalls';
import { useApiProgress } from '../common/ApiProgress';
import ButtonWithLoading from './ButtonWithLoading';
import FeedList from './FeedList';
import ProfileImage from './ProfileImage';

const UserFeed = () => {


    const { image } = useSelector((store: any) => {
        return { image: store.image }
    })
    const [focused, setFocused] = useState(false);
    const [feed, setFeed] = useState("");
    const pendingApiCall = useApiProgress("post", "/api/1.0/feeds");
    const feedRef = useRef(null);
    const [errors, setErrors] = useState({});
    

    useEffect(() => {
        const feedTracker = (event: any) => {
            //console.log(event.target);
            if (feedRef.current && !(feedRef.current as any).contains(event.target)) {
                setFocused(false);
                setFeed("");
            }
        }
        document.addEventListener("click", feedTracker);

        return () => {
            document.removeEventListener("click", feedTracker);
        }

    });

    const sendFeed = async () => {
        try {
            const data = {
                content: feed
            }
            const response = await sendFeedApi(data)
        } catch (error: any) {
            if (error.response.data.validationerrors) { setErrors(error.response.data.validationerrors); }
        }
    }

    const { content } = errors as any;

    return (
        <>
            <div className='card p-1 flex-row' ref={feedRef} >
                <div className='m-auto'>
                    <ProfileImage imageDb={image} width={32} />
                </div>
                <div className='card-body'>
                    <textarea
                        className={content ? 'form-control is-invalid' : 'form-control'}
                        rows={focused ? 3 : 1}
                        value={feed}
                        onFocus={() => { setFocused(true) }}
                        //onBlur={() => { setFocused(false) }}
                        onChange={(event: any) => { setFeed(event.target.value) }}
                    ></textarea>
                    <div className="invalid-feedback">{content}</div>
                </div>

                {focused && <div className='m-auto'>
                    <ButtonWithLoading onClick={sendFeed} disabled={pendingApiCall} pendingApiCall={pendingApiCall} text={t("Send")} />
                </div>}


            </div>
        </>

    );
};

export default UserFeed;
import React, { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { getFeeds } from '../api/ApiCalls';
import { useApiProgress } from '../common/ApiProgress';
import FeedItem from './FeedItem';
import Spinner from './Spinner';

const FeedList = () => {

    const [pagingFeed, setPagingFeed] = useState({
        content: [],
        last: true,
        number: 0
    });
    const { t } = useTranslation();
    const pendingApiCall = useApiProgress("get", "/api/1.0/feeds");
    const loadFeed = async (page = 0, size = 5) => {
        try {
            // if (!pendingApiCall) {
            const response = await getFeeds(page, size);
            setPagingFeed((previous) => {
                return {
                    ...response.data,
                    content: [...previous.content, ...response.data.content]
                }
            });
            // }
        } catch (error) {

        }
    }

    useEffect(() => {
        loadFeed();
    }, []);


    if (pagingFeed.content.length === 0) {
        return (<div><div className="alert alert-dark text-center" role="alert">
            {pendingApiCall ? <Spinner /> : t("There are no feeds")}
        </div></div>)
    }

    return (
        <div className='card mt-1 p-1'>
            {
                pagingFeed.content.map((feed: any) => { return <FeedItem key={feed.id} feed={feed.content} /> })
            }
            {!pagingFeed.last && (
                <div
                    onClick={pendingApiCall ? () => { } : () => { loadFeed(pagingFeed.number + 1) }}
                    className="alert alert-dark text-center card-footer"
                    style={pendingApiCall ? { cursor: "not-allowed" } : { cursor: "pointer" }}
                    role="alert">
                    {pendingApiCall ? <Spinner /> : t("Load old feeds")}
                </div>)}
        </div>



    );
};

export default FeedList;
import React, { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { useParams } from "react-router-dom";
import { getFeeds, getNewFeedsCount } from "../api/ApiCalls";
import { useApiProgress } from "../common/ApiProgress";
import FeedItem from "./FeedItem";
import Spinner from "./Spinner";

const FeedList = () => {
  const [pagingFeed, setPagingFeed] = useState({
    content: [],
    last: true,
    number: 0,
  });
  const { t } = useTranslation();
  const { username } = useParams() as any;
  const pendingApiCall = useApiProgress("get", "feeds");
  const [countNew, setCountNew] = useState(0);

  let lastId = 0;
  let firstId = 0;
  if (pagingFeed.content.length > 0) {
    firstId = (pagingFeed.content[0] as any).id;
    const lastIndex = pagingFeed.content.length - 1;
    lastId = (pagingFeed.content[lastIndex] as any).id;
  }

  useEffect(() => {
    const getCount = async () => {
      const response = await getNewFeedsCount(username, firstId);
      setCountNew(response.data.count);
    };
    let looper = setInterval(getCount, 5000);
    return function cleanup() {
      clearInterval(looper);
    };
  }, [firstId, username]);

  useEffect(() => {
    const loadFeedFirst = async () => {
      try {
        // if (!pendingApiCall) {
        const response = await getFeeds(username);
        setPagingFeed((previous) => {
          return {
            ...response.data,
            content: [...previous.content, ...response.data.content],
          };
        });

        // }
      } catch (error) {}
    };

    loadFeedFirst();
  }, [username]);

  const loadOldFeed = async () => {
    const response = await getFeeds(username, undefined, undefined, lastId, 1);
    setPagingFeed((previous) => {
      return {
        ...response.data,
        content: [...previous.content, ...response.data.content],
      };
    });
  };

  const loadNewFeed = async () => {
    if (pagingFeed.content.length > 0) {
      const response = await getFeeds(
        username,
        undefined,
        undefined,
        firstId,
        0
      );
      setPagingFeed((previous: any) => {
        return {
          ...previous,
          content: [...response.data.content, ...previous.content],
        };
      });
      setCountNew(0);
    }
  };

  if (pagingFeed.content.length === 0) {
    return (
      <div>
        <div className="alert alert-dark text-center" role="alert">
          {pendingApiCall ? <Spinner /> : t("There are no feeds")}
        </div>
      </div>
    );
  }

  const onDeleteFeedCallBack = (id: number) => {
    const filtered = pagingFeed.content.filter((feed: any) => {
      return feed.id !== id;
    });

    setPagingFeed((prew) => {
      return { ...prew, content: filtered };
    });
  };

  return (
    <div className="card mt-1 p-1">
      {countNew > 0 && (
        <div
          onClick={pendingApiCall ? () => {} : loadNewFeed}
          className="alert alert-dark text-center card-footer"
          style={
            pendingApiCall ? { cursor: "not-allowed" } : { cursor: "pointer" }
          }
          role="alert"
        >
          {pendingApiCall ? <Spinner /> : t("There are new feeds")}
        </div>
      )}
      {pagingFeed.content.map((feed: any) => {
        return (
          <FeedItem
            key={feed.id}
            feed={feed}
            onDeleteFeedCallBack={onDeleteFeedCallBack}
          />
        );
      })}
      {!pagingFeed.last && (
        <div
          onClick={pendingApiCall ? () => {} : loadOldFeed}
          className="alert alert-dark text-center card-footer"
          style={
            pendingApiCall ? { cursor: "not-allowed" } : { cursor: "pointer" }
          }
          role="alert"
        >
          {pendingApiCall ? <Spinner /> : t("Load old feeds")}
        </div>
      )}
    </div>
  );
};

export default FeedList;

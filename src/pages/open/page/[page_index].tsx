import React, { useCallback, useEffect, useState } from "react";
import { GetStaticPropsContext, InferGetStaticPropsType } from "next";
import Pagination from "../../../components/Pagination";
import Tag from "../../../components/Tag";
import { useRouter } from "next/router";

interface NoticeProps {}

const OpenNotice = ({
  OpenNoticeList,
}: InferGetStaticPropsType<typeof getStaticProps>) => {
  const router = useRouter();

  const [activePage, setActivePage] = useState(1);

  const handlePaginationChange = useCallback((page: number) => {
    setActivePage(page);
    console.log(
      router.push({
        pathname: router.pathname,
        query: { page_index: page },
      })
    );
  }, []);

  useEffect(() => {
    if (router.isReady) {
      const queryValue = router.query["page_index"];
      if (
        typeof queryValue === "string" &&
        typeof parseInt(queryValue) === "number"
      ) {
        setActivePage(parseInt(queryValue));
      }
    }
  }, [router]);

  return (
    <div className={""}>
      <section>What's hot</section>
      <section>
        <div className={"border-b"}>
          <section>
            <div>select box</div>
            <input type="text" />
            <button>검색</button>
          </section>
          <section>
            <button>등록순</button>
            <button>조회순</button>
            <button>오픈일 순</button>
          </section>
        </div>
        <ul>
          <li>
            {OpenNoticeList.map((item) => (
              <OpenNoticeListItem key={item.id} {...item} />
            ))}
          </li>
        </ul>
        <Pagination
          activePage={activePage}
          totalItemsCount={32}
          onChange={handlePaginationChange}
        />
      </section>
    </div>
  );
};

export const getStaticPaths = async () => {
  // todo : 이곳에서 현재 page_index 에 관한 정보를 불러와서 getStaticProps 에 전달한다.
  return {
    paths: [
      { params: { page_index: "1" } },
      { params: { page_index: "2" } },
      { params: { page_index: "3" } },
      { params: { page_index: "4" } },
    ],
    fallback: true,
  };
};

export const getStaticProps = async ({ params }: GetStaticPropsContext) => {
  console.log(params);
  const exampleList: OpenNoticeListItemProps[] = [
    {
      openDate: "2022.12.22 (목) 18:00",
      registrationDate: "2022.12.18 (일) 12:30",
      title: "스탠딩에그 단독 콘서트",
      hits: 283,
      id: 1,
    },
    {
      openDate: "2022.12.22 (목) 18:00",
      registrationDate: "2022.12.18 (일) 12:30",
      title: "아이유 단독 콘서트",
      hits: 1234,
      tags: [{ title: "단독공연" }, { title: "특별공연" }],
      id: 2,
    },
    {
      openDate: "2022.12.22 (목) 18:00",
      registrationDate: "2022.12.18 (일) 12:30",
      title: "윤하 단독 콘서트",
      hits: 523,
      tags: [{ title: "특별공연" }],
      id: 3,
    },
  ];
  const exampleList2: OpenNoticeListItemProps[] = [
    {
      openDate: "2022.12.22 (목) 18:00",
      registrationDate: "2022.12.18 (일) 12:30",
      title: "포맨 단독 콘서트",
      hits: 2223,
      id: 4,
    },
    {
      openDate: "2022.12.22 (목) 18:00",
      registrationDate: "2022.12.18 (일) 12:30",
      title: "블랙핑크 단독 콘서트",
      hits: 15334,
      tags: [{ title: "단독공연" }],
      id: 5,
    },
    {
      openDate: "2022.12.22 (목) 18:00",
      registrationDate: "2022.12.18 (일) 12:30",
      title: "아이브 단독 콘서트",
      hits: 5523,
      tags: [{ title: "특별공연" }, { title: "단독공연" }],
      id: 6,
    },
  ];
  return {
    props: {
      OpenNoticeList: params?.page_index === "1" ? exampleList : exampleList2,
    },
  };
};

export default OpenNotice;

export interface TagProps {
  title: string;
  className?: string;
}

interface OpenNoticeListItemProps {
  openDate: string;
  title: string;
  registrationDate: string;
  tags?: TagProps[];
  hits: number;
  posterUrl?: string;
  id: number;
}

const OpenNoticeListItem = ({
  openDate,
  title,
  registrationDate,
  tags,
  hits,
  posterUrl,
}: OpenNoticeListItemProps) => {
  return (
    <div
      className={
        "grid grid-cols-[250px_auto_100px] gap-4 border-b py-3 px-4 h-28 mx-6"
      }
    >
      <section className={"flex flex-col gap-2 justify-center text-sm"}>
        <span>티켓 오픈일</span>
        <p>{openDate}</p>
      </section>
      <section className={"flex flex-col justify-center gap-2"}>
        <div>
          <div className={"flex gap-2"}>
            {tags?.map((tag, index) => (
              <Tag key={index} className={tag.className}>
                {tag.title}
              </Tag>
            ))}
          </div>
          <span>{title}</span>
        </div>
        <div className={"flex gap-6 text-gray-400 text-sm"}>
          <p>{registrationDate}</p>
          <div>|</div>
          <p>조회 {hits}</p>
        </div>
      </section>
      <section>
        {posterUrl ? (
          <img src={posterUrl} alt="poster" />
        ) : (
          <div className={"w-[65px] h-full bg-gray-200 m-auto"}></div>
        )}
      </section>
    </div>
  );
};

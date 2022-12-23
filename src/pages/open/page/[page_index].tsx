import React, { useCallback, useEffect, useState } from "react";
import { GetStaticPropsContext, InferGetStaticPropsType } from "next";
import Pagination from "../../../components/Pagination";
import { useRouter } from "next/router";
import Select, { SelectOption } from "../../../components/Select";
import SearchForm from "../../../components/SearchForm";
import OpenNoticeListItem, {
  OpenNoticeListItemProps,
} from "../../../components/OpenNoticeListItem";
import CategoryList, {
  OptionItemProps,
} from "../../../components/CategoryList";
import OpenNoticeListItemSkeleton from "../../../components/OpenNoticeListItemSkeleton";

interface NoticeProps {}

const options = [
  { value: "all", label: "전체" },
  { value: "concert", label: "콘서트" },
  { value: "musical", label: "뮤지컬" },
];
const categoryList = [
  { value: 1, label: "등록순" },
  { value: 2, label: "조회순" },
  { value: 3, label: "오픈일순" },
];

const OpenNotice = ({
  OpenNoticeList,
}: InferGetStaticPropsType<typeof getStaticProps>) => {
  const router = useRouter();
  const [activePage, setActivePage] = useState(1);
  const [selectedOption, setSelectedOption] = useState<SelectOption>(
    options[0]
  );
  const [selectedCategoryOption, setSelectedCategoryOption] =
    useState<OptionItemProps>(categoryList[0]);

  const handleSelectChange = (value: any) => {
    if (value) {
      setSelectedOption(value);
    }
  };

  const handlePaginationChange = useCallback(async (page: number) => {
    setActivePage(page);
    await router.push({
      pathname: router.pathname,
      query: { page_index: page },
    });
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
      <section>
        <div className={"border-b flex items-center justify-between h-16 mx-4"}>
          <section className={"flex items-center h-8 text-sm"}>
            <div className={"w-36"}>
              <Select
                options={options}
                value={selectedOption}
                onChange={handleSelectChange}
              />
            </div>
            <SearchForm
              className={{
                form: `h-8 rounded px-4 min-w-[150px] w-[225px]`,
                input: "w-[100px]",
              }}
              onSubmit={(value) => console.log(value)}
              iconSize={"sm"}
            />
          </section>
          <CategoryList
            list={categoryList}
            value={selectedCategoryOption}
            onChange={(item) => setSelectedCategoryOption(item)}
          />
        </div>
        <ul>
          <li>
            {OpenNoticeList?.map((item) => (
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
    paths: [{ params: { page_index: "1" } }],
    fallback: true,
  };
};

export const getStaticProps = async ({ params }: GetStaticPropsContext) => {
  // console.log(params);
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

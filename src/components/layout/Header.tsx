import { useRouter } from "next/router";
import Link from "next/link";
import SearchForm from "../SearchForm";
import React from "react";
import CategoryList, { OptionItemProps } from "../CategoryList";

interface NavListTypes {
  title: string;
  pathname: string;
  query?: any;
}
const NavList: NavListTypes[] = [
  { title: "콘서트", pathname: "/concert" },
  { title: "뮤지컬", pathname: "/musical" },
  {
    title: "티켓 오픈공지",
    pathname: "/open/page/[page_index]",
    query: { page_index: 1 },
  },
];
const Header = () => {
  const router = useRouter();

  return (
    <div
      className={"grid grid-cols-6 grid-rows-[2em_auto] border col-span-full"}
    >
      <TopHeader />
      <section className={"col-start-2 col-end-6 flex flex-col border-x"}>
        <section className={"flex flex-1 justify-start items-center"}>
          <span
            className={
              "text-2xl font-bold mx-4 text-jin-blue-400 hover:text-jin-blue-500"
            }
          >
            <Link href={"/"}>TICKET</Link>
          </span>
          <SearchForm
            onSubmit={(value) => console.log(value)}
            placeholder={"검색어를 입력해주세요."}
            className={{ form: "border-jin-blue-400 rounded-2xl px-6" }}
          />
        </section>
        <nav className={"h-8 border-t px-8"}>
          <ul className={"flex items-center h-full"}>
            {NavList.map((list) => (
              <li key={list.pathname}>
                <Link
                  href={{
                    pathname: list.pathname,
                    ...(list.query && { query: list.query }),
                  }}
                  className={`font-bold ${
                    router.pathname === list.pathname ? "text-jin-blue-400" : ""
                  } mx-2 text-sm hover:text-jin-blue-500`}
                >
                  {list.title}
                </Link>
              </li>
            ))}
          </ul>
        </nav>
      </section>
    </div>
  );
};

export default Header;

const TopHeader = () => {
  const router = useRouter();
  const categoryList = [
    { value: "/signIn", label: "로그인" },
    { value: "/signUp", label: "회원가입" },
  ];

  const handleChange = async (category: OptionItemProps) => {
    console.log(category);
    await router.push({ pathname: category.value as string });
  };

  return (
    <header className={"col-span-full grid grid-cols-6 border-b"}>
      <section
        className={`col-start-2 col-end-6 border-x flex items-center justify-end`}
      >
        <CategoryList
          list={categoryList}
          onChange={handleChange}
          className={"font-bold text-xs mx-4 text-gray-500"}
        />
      </section>
    </header>
  );
};

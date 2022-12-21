import Link from "next/link";
import React, { useState } from "react";
import SearchForm from "../SearchForm";
import { useRouter } from "next/router";

interface LayoutProps {
  children: React.ReactNode;
}

const Layout = ({ children }: LayoutProps) => {
  return (
    <div className={"grid grid-cols-6 grid-rows-[8em_auto] h-full"}>
      <Header />
      <aside className={"col-span-1"}>aside</aside>
      <section className={"border-l col-span-5"}>{children}</section>
    </div>
  );
};

export default Layout;

interface NavListTypes {
  title: string;
  href: string;
}
const Header = () => {
  const NavList: NavListTypes[] = [
    { title: "콘서트", href: "/concert" },
    { title: "뮤지컬", href: "/musical" },
  ];

  const router = useRouter();

  return (
    <div
      className={"grid grid-cols-6 grid-rows-[2em_auto] border col-span-full"}
    >
      <header className={"col-span-full border-b"}>header</header>
      <section className={"col-start-2 col-end-6 flex flex-col border-x"}>
        <section className={"flex flex-1 justify-start items-center"}>
          <span
            className={
              "text-2xl font-bold mx-4 text-orange-400 hover:text-orange-500"
            }
          >
            <Link href={"/"}>TICKET</Link>
          </span>
          <SearchForm
            onSubmit={(value) => console.log(value)}
            placeholder={"검색어를 입력해주세요."}
            className={"border-orange-400"}
          />
        </section>
        <nav className={"h-8 border-t px-8"}>
          <ul className={"flex items-center h-full"}>
            {NavList.map((list) => (
              <li>
                <Link
                  href={list.href}
                  className={`font-bold ${
                    router.pathname === list.href ? "text-orange-400" : ""
                  } mx-2 text-sm hover:text-orange-500`}
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

import React from "react";
import Header from "./Header";

interface LayoutProps {
  children: React.ReactNode;
}

const Layout = ({ children }: LayoutProps) => {
  return (
    <div className={"grid grid-cols-6 grid-rows-[8em_auto] h-full"}>
      <Header />
      <aside className={"col-span-1"}>aside</aside>
      <section className={"border-l col-span-4 border-r"}>{children}</section>
      <aside className={"col-span-1"}>aside2</aside>
    </div>
  );
};

export default Layout;

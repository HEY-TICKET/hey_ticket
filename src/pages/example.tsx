import type { ReactElement } from "react";
import type { NextPageWithLayout } from "./_app";
import OtherLayout from "../components/layout/otherLayout";

const Page: NextPageWithLayout = () => {
  return <p>hello posts!</p>;
};

Page.getLayout = function getLayout(page: ReactElement) {
  return <OtherLayout>{page}</OtherLayout>;
};

export default Page;

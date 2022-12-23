import React from "react";

const skeletonCss = `animate-pulse bg-slate-300 text-slate-300 rounded w-fit h-5`;

const OpenNoticeListItemSkeleton = ({}) => {
  return (
    <div
      className={`grid grid-cols-[200px_auto_100px] gap-4 border-b py-3 px-4 h-28 mx-4 bg-slate-100`}
    >
      <section className={"flex flex-col gap-2 justify-center text-sm"}>
        <div className={`${skeletonCss} w-24`}></div>
        <div className={`text-xs ${skeletonCss} w-32`}></div>
      </section>
      <section className={"flex flex-col justify-center gap-2"}>
        <div className={"flex gap-2"}>
          <div className={`${skeletonCss} w-16`}></div>
        </div>
        <div className={`${skeletonCss} w-96`}></div>
        <div className={`flex gap-4 text-gray-400 text-xs`}>
          <div className={`${skeletonCss} w-36`}></div>
          <div>|</div>
          <div className={`${skeletonCss} w-14`}></div>
        </div>
      </section>
      <section>
        <div
          className={`${skeletonCss} w-[65px] bg-gray-200 m-auto h-[100%]`}
        ></div>
      </section>
    </div>
  );
};

export default OpenNoticeListItemSkeleton;
